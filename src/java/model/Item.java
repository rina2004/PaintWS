/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author anhbu
 */
public class Item {

    public Product product;
    public int quantity;
    private double price;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getUnitPrice() * quantity; // Tính toán giá trị của Item
    }

    public Item() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.price = product.getUnitPrice() * this.quantity; // Cập nhật giá khi sản phẩm thay đổi
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.price = product.getUnitPrice() * quantity; // Cập nhật giá khi số lượng thay đổi
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item{" + "product=" + product + ", quantity=" + quantity + ", price=" + price + '}';
    }
}
