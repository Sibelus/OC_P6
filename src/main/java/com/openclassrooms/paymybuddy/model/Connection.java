package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "connection")
public class Connection {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Id
    @Column(name = "friend_id")
    private int friendId;


    // GETTERS & SETTERS
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
