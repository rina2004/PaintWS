/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author A A
 */
public class MonthlySales {
    private int month;
    private double revenue;

    public MonthlySales(int month, double revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public MonthlySales() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
    
    /**
     * Get month name from month number
     * @return Month name
     */
    public String getMonthName() {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                              "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }

    @Override
    public String toString() {
        return "MonthlySales{" + "month=" + getMonthName() + ", revenue=" + revenue + '}';
    }
}