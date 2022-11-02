package com.tsa.serialize.entity;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private final Date data;
    private String message;
    private final double amount;

    public Message(Date data, String message, double amount) {
        this.data = data;
        this.message = message;
        this.amount = amount;
    }

    public Date getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", amount=" + amount +
                '}';
    }
}
