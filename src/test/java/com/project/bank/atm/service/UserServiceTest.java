package com.project.bank.atm.service;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.bank.atm.dao.ATMamount;
import com.project.bank.atm.dao.User;
import com.project.bank.atm.main.ContextCreation;
import com.project.bank.atm.repository.UserRepository;
import com.project.bank.atm.util.Withdraw;
/**
 * @author raviteja karumuri
 *
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserService.class)
class UserServiceTest  extends ContextCreation{
	
	@Autowired
	private UserService userService;
	
	
	private Map<Integer,Integer> dispense_amount;
	private User dummy_user;
	
	@MockBean
	public Withdraw withdraw;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ATMService atmService;
	
	@MockBean
	private SecurityContext context;
	
	@MockBean
	private Authentication authentication;
	
	
	@BeforeEach
	public void setup() {
		
		
		dummy_user = new User();
		dummy_user.setAccountnumber("123");
		
		dispense_amount = new HashMap<>();
		dispense_amount.put(50, 10);
		dispense_amount.put(20, 5);
		dispense_amount.put(10, 0);
		dispense_amount.put(5, 0);
		
		
	    Mockito.when(authentication.getPrincipal()).thenReturn(dummy_user);
	    Mockito.when(context.getAuthentication()).thenReturn(authentication);
	    SecurityContextHolder.setContext(context);
		
		Mockito.when(withdraw.withdraw(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any())).thenReturn(dispense_amount);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
		Mockito.when(userRepository.findByAccountnumber(Mockito.any())).thenReturn(dummy_user);
		Mockito.when(atmService.findByLocation(Mockito.anyString())).thenReturn(new ATMamount());

	}

    @Test
    void findByAccountnumber() {
    	User response = userService.findByAccountnumber();
    	Assertions.assertThat(dummy_user.getAccountnumber()).isEqualTo(response.getAccountnumber());
    
    }

    @Test
    void withdraw() {
    	Map<Integer,Integer> response = userService.withdraw(600, "ifsc", new StringBuffer(" "));
    	Assertions.assertThat(dispense_amount.toString()).isEqualTo(response.toString());
    }
}