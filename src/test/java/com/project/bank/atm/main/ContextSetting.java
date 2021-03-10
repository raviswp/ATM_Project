package com.project.bank.atm.main;

import com.project.bank.atm.AtmApplication;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
/**
 * @author raviteja karumuri
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ContextSetting {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    private  FilterChainProxy springSecurityFilterChain;

    protected MockMvc mockMvc;

    @BeforeEach
    public void applySecurityToContext() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                            .apply(springSecurity(springSecurityFilterChain))
                            .build();
    }
}
