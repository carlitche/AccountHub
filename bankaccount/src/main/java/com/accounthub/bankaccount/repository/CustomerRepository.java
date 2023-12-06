package com.accounthub.bankaccount.repository;

import com.accounthub.bankaccount.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
