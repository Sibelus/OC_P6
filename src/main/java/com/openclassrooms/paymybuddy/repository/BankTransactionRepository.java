package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Integer> {
    List<BankTransaction> findByUserId(int id);
}
