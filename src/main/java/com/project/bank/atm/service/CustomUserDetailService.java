package com.project.bank.atm.service;


import com.project.bank.atm.dao.User;
import com.project.bank.atm.repository.UserRepository;
import com.project.bank.atm.securityutil.CustomUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * @author raviteja karumuri
 *
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    private static final Log logger = LogFactory.getLog(CustomUserDetailService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String accountnumber) throws UsernameNotFoundException {
        User user = userRepository.findByAccountnumber(accountnumber);
        if(user == null)
            throw new UsernameNotFoundException("AccountNumber: "+accountnumber+" "+"not found");
        return new CustomUser(user);
    }
}
