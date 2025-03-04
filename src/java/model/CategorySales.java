/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author A A
 */
public class CategorySales {
    private String categoryName;
    private int totalSold;

    public CategorySales(String categoryName, int totalSold) {
        this.categoryName = categoryName;
        this.totalSold = totalSold;
    }

    public CategorySales() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    @Override
    public String toString() {
        return "CategorySales{" + "categoryName=" + categoryName + ", totalSold=" + totalSold + '}';
    }
}
