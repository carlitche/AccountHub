package com.accounthub.bankaccount.repository;

import com.accounthub.bankaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
