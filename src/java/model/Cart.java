/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anhbu
 */
public class Cart {

    public List<Item> listItems;

    public Cart() {
        listItems = new ArrayList<>();
    }

    public List<Item> getListItems() {
        return listItems;
    }

    public void setListItems(List<Item> listItems) {
        this.listItems = listItems;
    }

    public Item getItemByID(int id) {
        for (Item item : listItems) {
            if (item.getProduct().getProductID() == id) {
                return item;
            }
        }
        return null;
    }

    public int getQuantityByID(int id) {
        Item item = getItemByID(id);
        return (item != null) ? item.getQuantity() : 0; // Trả về 0 nếu item là null
    }

    public void addItem(Item t) {
        if (getItemByID(t.getProduct().getProductID()) != null) {
            Item m = getItemByID(t.getProduct().getProductID());
            m.setQuantity(m.getQuantity() + t.getQuantity());
        } else {
            listItems.add(t);
        }
    }

    public void removeItem(int id) {
        if (getItemByID(id) != null) {
            listItems.remove(getItemByID(id));
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (Item item : listItems) {
            total += item.getPrice(); // Giá đã được tính trong Item
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public List<Product> getlistProducts() {
        List<Product> listP = new ArrayList<>();
        for (Item item : listItems) {
            listP.add(item.getProduct());
        }
        return listP;
    }

    // Phương thức giảm số lượng của sản phẩm trong giỏ hàng
    public void decreaseItem(int id) {
        Item item = getItemByID(id);
        if (item != null) {
            int newQuantity = item.getQuantity() - 1;
            if (newQuantity > 0) {
                item.setQuantity(newQuantity); // Giảm số lượng nếu lớn hơn 0
            } else {
                removeItem(id); // Xóa sản phẩm khỏi giỏ hàng nếu số lượng <= 0
            }
        }
    }
}
