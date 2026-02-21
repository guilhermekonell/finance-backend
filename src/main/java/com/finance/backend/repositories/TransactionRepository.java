package com.finance.backend.repositories;

import com.finance.backend.domains.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByHashIn(Set<String> hashes);

}
