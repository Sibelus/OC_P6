package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_transaction")
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "bank_id")
    private int bankId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "comment")
    private String comment;

    @Column(name = "fee")
    private float fee;


    // GETTERS & SETTERS
    public int getTransactionId() {
        return id;
    }

    public void setTransactionId(int transactionId) {
        this.id = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public float getAmount() {
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
                ", userId=" + userId +
                ", bankId=" + bankId +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", fee=" + fee +
                '}';
    }
}
