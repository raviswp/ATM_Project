package com.project.bank.atm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bank.atm.dao.User;

import com.project.bank.atm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * @author raviteja karumuri
 *
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/balance", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String>  balancecheck(){
        User user = userService.findByAccountnumber();
        int balance = user.getAccount().getBalance();
        int totalBalance = user.getAccount().getOverdraft() + balance;
        String responseString = "Available balance is::"+String.valueOf(balance)+" Maximum Withdraw Possible::"+String.valueOf(totalBalance);
        return ResponseEntity.ok(responseString);
    }


    @RequestMapping(value = "/withdraw", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> withdraw(@RequestParam int withdraw_amount, @RequestParam String location) throws JsonProcessingException{
        StringBuffer message = new StringBuffer();
        Map<Integer,Integer> dispense_amount = userService.withdraw(withdraw_amount, location, message);
        if((dispense_amount.get(50)>0) || (dispense_amount.get(20)>0) || (dispense_amount.get(10)>0) || (dispense_amount.get(5)>0))
            return ResponseEntity.ok().body((new ObjectMapper().writeValueAsString(dispense_amount)+" -- "+message.toString()));
        return ResponseEntity.badRequest().body(message.toString());
    }
}
