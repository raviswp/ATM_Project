package com.project.bank.atm;

import com.project.bank.atm.dao.User;
import com.project.bank.atm.dao.UserRole;
import com.project.bank.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
/**
 * @author raviteja karumuri
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class AtmApplication {


	public static void main(String[] args) {


		SpringApplication.run(AtmApplication.class, args);
	}
}

