package com.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByTxnId(String txnId);

}
