package com.project.bank.atm.util;

import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.bank.atm.dao.ATMamount;
import com.project.bank.atm.dao.Account;
import com.project.bank.atm.dao.User;
import com.project.bank.atm.dao.UserRole;
/**
 * @author raviteja karumuri
 *
 */
class WithdrawTest {
	

	private Withdraw dummy_withDraw;
	private ATMamount dummy_ATMamount;
	private User dummy_user;
	private Account dummy_account;
	
	@BeforeEach
	public void setup() {
		dummy_withDraw = new Withdraw();
		dummy_ATMamount = new ATMamount();
		dummy_ATMamount.setFifty(10);
		dummy_ATMamount.setTwenty(20);
		dummy_ATMamount.setTen(20);
		dummy_ATMamount.setFive(10);
		
		dummy_account = new Account();
		dummy_account.setBalance(600);
		dummy_account.setOverdraft(200);
		
		Set<UserRole> dummy_roles = new HashSet<>();
		dummy_roles.add(UserRole.ROLE_USER);
		
		dummy_user = new User();
		dummy_user.setAccountnumber("123");
		dummy_user.setPin(new BCryptPasswordEncoder().encode("123"));
		dummy_user.setRoles(dummy_roles);
		dummy_user.setAccount(dummy_account);
	}
	

    @Test
    void withdraw() {
    	Map<Integer,Integer> expected = new HashMap<>();
    	expected.put(50, 10);
    	expected.put(20, 5);
    	expected.put(10, 0);
    	expected.put(5, 0);
    	Map<Integer,Integer> response = dummy_withDraw.withdraw(dummy_ATMamount, dummy_user, 600, new StringBuffer());
    	Assertions.assertThat(expected.toString()).isEqualTo(response.toString());
    }
    
    @Test
    @DisplayName("Withdraw Total Money in ATM")
    void withdraw_one() {
    	Map<Integer,Integer> expected = new HashMap<>();
    	expected.put(50, 10);
    	expected.put(20, 20);
    	expected.put(10, 20);
    	expected.put(5, 10);
    	dummy_account.setBalance(1150);
    	dummy_user.setAccount(dummy_account);
    	Map<Integer,Integer> response = dummy_withDraw.withdraw(dummy_ATMamount, dummy_user, 1150, new StringBuffer());
    	Assertions.assertThat(expected.toString()).isEqualTo(response.toString());
   
    }
    
    @Test
    @DisplayName("Trying to withdraw More Money Than ATM has")
    void withdraw_two() {
    	dummy_account.setBalance(1160);
    	dummy_user.setAccount(dummy_account);
    	StringBuffer dummy_message = new StringBuffer();
    	dummy_withDraw.withdraw(dummy_ATMamount, dummy_user, 1160, dummy_message);
    	String expected = "Available balance in ATM : 1150 but requested amount : 1160";
    	Assertions.assertThat(expected).isEqualTo(dummy_message.toString());
   
    }
    
    @Test
    @DisplayName("Trying to withdraw More Money Than User has")
    void withdraw_three() {
    	StringBuffer dummy_message = new StringBuffer();
    	dummy_withDraw.withdraw(dummy_ATMamount, dummy_user, 850, dummy_message);
    	String expected = "Available balance in user account : 800 but requested amount : 850";
    	Assertions.assertThat(expected).isEqualTo(dummy_message.toString());
   
    }
}