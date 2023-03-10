package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    List<Connection> findByUserId(int userId);

    boolean existsByUserIdAndFriendId(int userId, int friendId);
}