package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "connection")
public class Connection {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "friend_id")
    private User friend;


    // GETTERS & SETTERS
    public int getConnectionId() {
        return id;
    }

    public void setConnectionId(int connectionId) {
        this.id = connectionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "connectionId=" + id +
                ", user=" + user +
                ", friend=" + friend +
                '}';
    }
}
