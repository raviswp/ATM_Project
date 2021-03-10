package com.project.bank.atm.securityutil;

import com.project.bank.atm.dao.User;
import com.project.bank.atm.dao.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
/**
 * @author raviteja karumuri
 *
 */
public class CustomUser extends org.springframework.security.core.userdetails.User {



    public  CustomUser(final User user){
        super(user.getAccountnumber(), user.getPin(), true, true, true, !isAccountLocked(user) , buildAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> buildAuthorities(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<UserRole> userRoles = user.getRoles();

        if (userRoles != null) {
            userRoles.forEach((role)->{
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
                authorities.add(authority);
            });
        }
        return authorities;
    }

    private static boolean isAccountLocked(User user) {
        boolean result = true;
        switch (user.getStatus()){

            case NonBlock:
            case Block:
                result = false;
                break;
        }
        return result;
    }


}
