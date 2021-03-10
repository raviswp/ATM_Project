package com.project.bank.atm.controller;

import com.project.bank.atm.dao.ATMamount;


import com.project.bank.atm.main.ContextCreation;
import com.project.bank.atm.service.ATMService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.assertj.core.api.Assertions;
/**
 * @author raviteja karumuri
 *
 */

@WebMvcTest(AdminController.class)
@AutoConfigureJsonTesters
class AdminControllerTest extends ContextCreation {

	@Autowired
	private MockMvc mockMvc;
	
    @MockBean
    private ATMService atmService;
    
    @Autowired
    private JacksonTester<ATMamount> jsonTester;
    
    private ATMamount atMamount = new ATMamount();

    @BeforeEach
    public void setup() throws Exception{
        atMamount.setFifty(10);
        atMamount.setTwenty(20);
        atMamount.setTen(20);
        atMamount.setFive(10);
        Mockito.when(atmService.findByLocation(Mockito.anyString())).thenReturn(atMamount);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void availablenotes() throws Exception{
       MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/admin/atmnotes")
    		   		.param("location", "ifsc").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
    		   		.andReturn().getResponse();
       Assertions.assertThat(response.getStatus()).isEqualTo(200);
       Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(atMamount).getJson());
    }
    
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Checking the Controller with role other than Admin")
    void notes() throws Exception{
       MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/admin/atmnotes")
    		   		.param("location", "ifsc").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
    		   		.andReturn().getResponse();
       Assertions.assertThat(response.getStatus()).isEqualTo(403);       
    }
}