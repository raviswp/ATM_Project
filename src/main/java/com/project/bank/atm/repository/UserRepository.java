package com.project.bank.atm.repository;

import com.project.bank.atm.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author raviteja karumuri
 *
 */
@Repository
public interface  UserRepository extends JpaRepository<User,Long> {
    User findByAccountnumber(String accountnumber);
}
