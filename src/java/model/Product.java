/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author anhbu
 */
public class Product {
    public int productID;
    private String productName;
    private double volume;
    private String color;
    private double unitPrice;
    public int unitsInStock;
    private int quantitySold;
    private boolean discontinued;
    private String image;
    private String description;
    private double discount;
    private boolean status;
    private Category category;
    private Supplier supplier;

    public Product(int productID, String productName, double volume, String color, double unitPrice, int unitsInStock, int quantitySold, boolean discontinued, String image, String description, double discount, boolean status, Category category, Supplier supplier) {
        this.productID = productID;
        this.productName = productName;
        this.volume = volume;
        this.color = color;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.quantitySold = quantitySold;
        this.discontinued = discontinued;
        this.image = image;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.category = category;
        this.supplier = supplier;
    }

    public Product() {
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", volume=" + volume + ", color=" + color + ", unitPrice=" + unitPrice + ", unitsInStock=" + unitsInStock + ", quantitySold=" + quantitySold + ", discontinued=" + discontinued + ", image=" + image + ", description=" + description + ", discount=" + discount + ", status=" + status + ", category=" + category + ", supplier=" + supplier + '}';
    }
    
    
}
