package com.project.bank.atm.service;

import com.project.bank.atm.dao.ATMamount;
import com.project.bank.atm.repository.ATMRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
/**
 * @author raviteja karumuri
 *
 */
@Service
public class ATMService {

	private static final Log logger = LogFactory.getLog(ATMService.class);
	
    @Autowired
    private ATMRepository atmRepository;

    public ATMamount findByLocation(String location){
      ATMamount atmamount =  atmRepository.findByLocation(location);
      return atmamount;
    }

    @Transactional
    public void save(ATMamount atMamount) {
        atmRepository.save(atMamount);
    }
}
