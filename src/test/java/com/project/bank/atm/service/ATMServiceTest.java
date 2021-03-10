package com.project.bank.atm.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.bank.atm.dao.ATMamount;
import com.project.bank.atm.main.ContextCreation;
import com.project.bank.atm.repository.ATMRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
/**
 * @author raviteja karumuri
 *
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(ATMService.class)
class ATMServiceTest extends ContextCreation{
	
	@MockBean
	private ATMRepository atmRepository;
	
	@Autowired
	private ATMService atmService;
	
	private ATMamount dummy_atmAmount;
	
	@BeforeEach
	public void setup() {
		dummy_atmAmount = new ATMamount();
		dummy_atmAmount.setFifty(10);
		Mockito.when(atmRepository.findByLocation(Mockito.anyString())).thenReturn(dummy_atmAmount);
	}

    @Test
    void findByLocation() {
    	ATMamount response = atmService.findByLocation("ifsc");
    	Assertions.assertThat(dummy_atmAmount.getFifty()).isEqualTo(response.getFifty());
    }
   
}