package com.project.bank.atm.controller;

import com.project.bank.atm.dao.Account;

import com.project.bank.atm.dao.User;
import com.project.bank.atm.dao.UserRole;
import com.project.bank.atm.main.ContextCreation;
import com.project.bank.atm.service.UserService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * @author raviteja karumuri
 *
 */
@WebMvcTest(UserController.class)
@WithMockUser(roles="USER")
@AutoConfigureJsonTesters
public class UserControllerTest extends ContextCreation{

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	public UserService userService;
	
	@Autowired
	private JacksonTester<Map<Integer,Integer>> json;
	
	private Map<Integer,Integer> dummy_dispense_amount;
	
	@BeforeEach
	public void setup() {
		Account dummy_account = new Account();
		dummy_account.setBalance(300);
		dummy_account.setOverdraft(200);
		
		Set<UserRole> dummy_roles = new HashSet<>();
		dummy_roles.add(UserRole.ROLE_USER);
		
		User dummy_user = new User();
		dummy_user.setAccountnumber("123");
		dummy_user.setPin(new BCryptPasswordEncoder().encode("123"));
		dummy_user.setRoles(dummy_roles);
		dummy_user.setAccount(dummy_account);
		
		dummy_dispense_amount = new HashMap<>();
		dummy_dispense_amount.put(50, 6);
		dummy_dispense_amount.put(20, 0);
		dummy_dispense_amount.put(10, 0);
		dummy_dispense_amount.put(5, 0);
		
		Mockito.when(userService.findByAccountnumber()).thenReturn(dummy_user);
		Mockito.when(userService.withdraw(Mockito.anyInt(), Mockito.anyString(), Mockito.any())).thenReturn(dummy_dispense_amount);
		
	}
	 
	
	@Test
    public void balancecheck() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/balance").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().string("Available balance is::300 Maximum Withdraw Possible::500"));
	}

    @Test
    public void withdraw() throws Exception {
    	
    	MockHttpServletResponse response =  mockMvc.perform(MockMvcRequestBuilders.post("/withdraw")
    			.accept(MediaType.APPLICATION_JSON)
    			.contentType(MediaType.APPLICATION_JSON)
    			.param("withdraw_amount", "300")
    			.param("location", "ifsc"))
    			.andReturn().getResponse();
    	Assertions.assertThat(response.getContentAsString()).isEqualTo(json.write(dummy_dispense_amount).getJson()+" -- ");
    			
    }
  
}