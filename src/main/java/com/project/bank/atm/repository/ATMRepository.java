package com.project.bank.atm.repository;

import com.project.bank.atm.dao.ATMamount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author raviteja karumuri
 *
 */
@Repository
public interface ATMRepository extends JpaRepository<ATMamount, Long> {
    ATMamount findByLocation(String location);
}
