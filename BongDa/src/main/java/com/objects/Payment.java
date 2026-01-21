
package com.objects;

import java.time.LocalDateTime;

public class Payment {
    private String payment_id;
    private String order_id;
    private String method;
    private int amount;
    private String status;
    private LocalDateTime payment_date;

    public Payment() {
    }

    public Payment(String payment_id, String order_id, String method, int amount, String status, LocalDateTime payment_date) {
        this.payment_id = payment_id;
        this.order_id = order_id;
        this.method = method;
        this.amount = amount;
        this.status = status;
        this.payment_date = payment_date;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }
    
}
