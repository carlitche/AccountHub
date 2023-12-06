package com.accounthub.banktransaction.repository;

import com.accounthub.banktransaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<List<Transaction>> findByAccountId(Long accountId);
}
