package com.project.bank.atm.util;

import com.project.bank.atm.dao.ATMamount;

import com.project.bank.atm.dao.User;
import com.project.bank.atm.service.CustomUserDetailService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
/**
 * @author raviteja karumuri
 *
 */
@Component
public class Withdraw {

	private static final Log logger = LogFactory.getLog(Withdraw.class);
    private Map<Integer,Integer> dispenseamount;
    private Map<Integer,Integer> atmnotes = new HashMap<>();
    private int atmbalance;
    private int reqamount;
    private int count = 20;
    private int divison = 50;
    private int processamount;
    private int accountnumber;
    private boolean flag = true;

    public Map<Integer,Integer> withdraw(ATMamount atmamount, User user, int withdraw_amount, StringBuffer message){
        atmnotes.put(50,atmamount.getFifty());
        atmnotes.put(20,atmamount.getTwenty());
        atmnotes.put(10,atmamount.getTen());
        atmnotes.put(5,atmamount.getFive());
        flag = true;
        dispenseamount = new HashMap<>();
        dispenseamount.put(50, 0);
        dispenseamount.put(20, 0);
        dispenseamount.put(10, 0);
        dispenseamount.put(5, 0);
        this.reqamount = withdraw_amount;
        this.accountnumber = Integer.parseInt(user.getAccountnumber());
        this.processamount = reqamount;
        atmbalance = (atmnotes.get(50)*50)+(atmnotes.get(20)*20)+(atmnotes.get(10)*10)+(atmnotes.get(5)*5);
        if((reqamount != 0) && (reqamount <= atmbalance) && (reqamount <= (user.getAccount().getBalance() + user.getAccount().getOverdraft()))) {
            while(flag) {
                int divamount = (processamount/divison);
                if((divamount) <= atmnotes.get(divison) && (processamount != 0)) {
                    dispenseamount.put(divison, divamount);
                    processamount = processamount - (dispenseamount.get(divison)*divison);
                    int updatenotes = (atmnotes.get(divison) - divamount);
                    atmnotes.put(divison, ((updatenotes<0)?0:updatenotes));
                    user.getAccount().setBalance(user.getAccount().getBalance() - reqamount);
                }
                else {
                    if(count == 20 && (processamount != 0)) {
                        dispenseamount.put(divison, atmnotes.get(divison));
                        atmnotes.put(divison, 0);
                        processamount = processamount - (dispenseamount.get(divison)*divison);
                        divison = count;
                        count = 10;

                    }
                    else if(count == 10 && (processamount != 0)){
                        dispenseamount.put(divison, atmnotes.get(divison));
                        atmnotes.put(divison, 0);
                        processamount = processamount - (dispenseamount.get(divison)*divison);
                        divison = count;
                        count = 5;
                    }
                    else if(count == 5 && (processamount != 0)){
                        dispenseamount.put(divison, atmnotes.get(divison));
                        atmnotes.put(divison, 0);
                        processamount = processamount - (dispenseamount.get(divison)*divison);
                        divison = count;
                        count = 50;
                    }
                    else
                        flag = false;
                }



            }

        }
        else if(reqamount > (user.getAccount().getBalance() + user.getAccount().getOverdraft())){
             message.append("Available balance in user account : "+(user.getAccount().getBalance() + user.getAccount().getOverdraft())+ " but requested amount : "+withdraw_amount);
        }

        else{
            message.append("Available balance in ATM : "+atmbalance+ " but requested amount : "+withdraw_amount);
        }

        atmamount.setFifty(atmnotes.get(50));
        atmamount.setTwenty(atmnotes.get(20));
        atmamount.setTen(atmnotes.get(10));
        atmamount.setFive(atmnotes.get(5));
        return dispenseamount;
    }

}
