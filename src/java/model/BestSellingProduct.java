/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author anhbu
 */
public class BestSellingProduct {

    private String productName;
    private int totalQuantitySold;

    // Constructor với các thuộc tính cần thiết
    public BestSellingProduct(String productName, int totalQuantitySold) {
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
    }

    // Constructor mặc định (nếu cần)
    public BestSellingProduct() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(int totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    @Override
    public String toString() {
        return "BestSellingProduct{" + "productName=" + productName + ", totalQuantitySold=" + totalQuantitySold + '}';
    }

}
