package com.project.bank.atm.controller;

import com.project.bank.atm.dao.ATMamount;

import com.project.bank.atm.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author raviteja karumuri
 *
 */
@RestController
public class AdminController {

    @Autowired
    private ATMService atmService;

    @RequestMapping(method = RequestMethod.GET, value = "/admin/atmnotes", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ATMamount> availablenotes(@RequestParam String location){

        ATMamount atMamount = atmService.findByLocation(location);
        return ResponseEntity.ok(atMamount);
    }
}
