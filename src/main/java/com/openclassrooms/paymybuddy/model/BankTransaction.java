package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_transaction")
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @Column(name = "user_id")
    private User user;

    @ManyToOne()
    @Column(name = "bank_id")
    private BankAccount bank;

    @Column(name = "amount")
    private int amount;

    @Column(name = "comment")
    private String comment;

    @Column(name = "fee")
    private float fee;


    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BankAccount getBank() {
        return bank;
    }

    public void setBank(BankAccount bank) {
        this.bank = bank;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    //TO STRING
    @Override
    public String toString() {
        return "BankTransaction{" +
                "id=" + id +
                ", user=" + user +
                ", bank=" + bank +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", fee=" + fee +
                '}';
    }
}
