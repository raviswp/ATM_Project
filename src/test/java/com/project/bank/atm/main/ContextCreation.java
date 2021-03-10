package com.project.bank.atm.main;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.bank.atm.service.CustomUserDetailService;
import com.project.bank.atm.util.CustomAuthenticationEntryPoint;
/**
 * @author raviteja karumuri
 *
 */

public class ContextCreation {
	
	
	@MockBean
	public  CustomUserDetailService custom;
	
	@MockBean
	public CustomAuthenticationEntryPoint entrypoint;
	

}
