
package com.objects;

import java.time.LocalDateTime;

public class Order {
    private String order_id;
    private String user_id;
    private LocalDateTime order_date;
    private int total_amount;
    private String status;

    public Order(String order_id, String user_id, LocalDateTime order_date, int total_amount, String status) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.status = status;
    }

    public Order() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDateTime order_date) {
        this.order_date = order_date;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
