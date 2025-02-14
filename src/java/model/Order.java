/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author anhbu
 */
public class Order {
    private String userID;
    private int orderId;
    private double total;
    Date date;
    private boolean status;

    public Order(String userID, int orderId, double total, Date date, boolean status) {
        this.userID = userID;
        this.orderId = orderId;
        this.total = total;
        this.date = date;
        this.status = status;
    }

    public Order() {
    }

    public String getUserName() {
        return userID;
    }

    public void setUserName(String userID) {
        this.userID = userID;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
     // Tính tổng giá trị đơn hàng từ giỏ hàng
    public void calculateTotalFromCart(Cart cart) {
        this.total = cart.getTotalPrice();
    }

    @Override
    public String toString() {
        return "Order{" + "userID=" + userID + ", orderId=" + orderId + ", total=" + total + ", date=" + date + ", status=" + status + '}';
    }
    
    
}
