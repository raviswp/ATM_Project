package com.project.bank.atm.service;

import com.project.bank.atm.dao.ATMamount;
import com.project.bank.atm.dao.User;
import com.project.bank.atm.repository.UserRepository;
import com.project.bank.atm.util.Withdraw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
/**
 * @author raviteja karumuri
 *
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ATMService atmService;

    private static final Log logger = LogFactory.getLog(UserService.class);
    
    @Autowired
    private Withdraw withdraw;

    public User findByAccountnumber(){
        User user = userRepository.findByAccountnumber(currentuser());
        return user;
    }

    public String currentuser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }

    @Transactional
    public Map<Integer,Integer> withdraw(int withdraw_amount, String location, StringBuffer message) {
        User currentUser= userRepository.findByAccountnumber(currentuser());
        ATMamount atMamount = atmService.findByLocation(location);
        Map<Integer,Integer> dispense_amount = withdraw.withdraw(atMamount, currentUser, withdraw_amount, message);
        userRepository.save(currentUser);
        atmService.save(atMamount);
        if(message.toString().equals(""))
            message.append("Available Balance after withdraw::"+ currentUser.getAccount().getBalance());
        return dispense_amount;
    }
}
