package com.project.bank.atm.config;

import com.project.bank.atm.service.CustomUserDetailService;
import com.project.bank.atm.util.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author raviteja karumuri
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customuserdetailservice;

    @Autowired
    private CustomAuthenticationEntryPoint entrypoint;
    


    @Override
    protected void configure(AuthenticationManagerBuilder registry) throws Exception {
        registry.userDetailsService(customuserdetailservice).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/webjars/**", "/json/**");
    }

    public void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().authenticationEntryPoint(entrypoint)
                .and()
//                .exceptionHandling().accessDeniedHandler(accessdenied)
//                .and()
                .authorizeRequests()
                    .antMatchers("/login","/h2-console/","/register").permitAll()
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();
    }



}
