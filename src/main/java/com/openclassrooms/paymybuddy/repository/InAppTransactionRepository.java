package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InAppTransactionRepository extends CrudRepository<InAppTransaction, Integer> {
}
