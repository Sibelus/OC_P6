package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InAppTransactionRepository extends JpaRepository<InAppTransaction, Integer> {
}
