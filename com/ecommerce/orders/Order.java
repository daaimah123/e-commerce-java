package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private int orderID;
    private Customer customer;
    private List<Product> products;
    private List<Integer> quantities;
    private double orderTotal;
    private String status;
    private LocalDateTime orderDate;
    
    public Order() {
        this.orderID = 0;
        this.customer = null;
        this.products = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.orderTotal = 0.0;
        this.status = "Pending";
        this.orderDate = LocalDateTime.now();
    }
    
    public Order(int orderID, Customer customer) {
        setOrderID(orderID);
        setCustomer(customer);
        this.products = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.orderTotal = 0.0;
        this.status = "Pending";
        this.orderDate = LocalDateTime.now();
        copyFromCustomerCart();
    }
    
    // Getters
    public int getOrderID() { return orderID; }
    public Customer getCustomer() { return customer; }
    public List<Product> getProducts() { return products; }
    public List<Integer> getQuantities() { return quantities; }
    public double getOrderTotal() { return orderTotal; }
    public String getStatus() { return status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    
    // Setters with validation
    public void setOrderID(int orderID) {
        if (orderID <= 0) {
            throw new IllegalArgumentException("Order ID must be a positive number. System error - please contact support.");
        }
        this.orderID = orderID;
    }
    
    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Order must have a valid customer. System error - please contact support.");
        }
        this.customer = customer;
    }
    
    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Order status cannot be empty.");
        }
        String[] validStatuses = {"Pending", "Confirmed", "Processing", "Shipped", "Delivered", "Cancelled"};
        boolean isValid = false;
        for (String validStatus : validStatuses) {
            if (validStatus.equalsIgnoreCase(status.trim())) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new IllegalArgumentException("Invalid order status. Valid options are: Pending, Confirmed, Processing, Shipped, Delivered, Cancelled");
        }
        this.status = status.trim();
    }
    
    private void copyFromCustomerCart() {
        if (customer.isCartEmpty()) {
            throw new IllegalArgumentException("Cannot create order from empty cart. Please add items to cart first.");
        }
        
        List<Product> cart = customer.getShoppingCart();
        List<Integer> cartQuantities = customer.getQuantities();
        
        for (int i = 0; i < cart.size(); i++) {
            products.add(cart.get(i));
            quantities.add(cartQuantities.get(i));
        }
        
        calculateTotal();
    }
    
    private void calculateTotal() {
        orderTotal = 0.0;
        for (int i = 0; i < products.size(); i++) {
            orderTotal += products.get(i).getPrice() * quantities.get(i);
        }
    }
    
    public void updateStatus(String newStatus) {
        try {
            setStatus(newStatus);
            System.out.println("Order #" + orderID + " status updated to: " + newStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Could not update order status: " + e.getMessage());
        }
    }
    
    public void displayOrderSummary() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");
        
        System.out.println("\n=== ORDER SUMMARY ===");
        System.out.println("Order #: " + orderID);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Order Date: " + orderDate.format(formatter));
        System.out.println("Status: " + status);
        System.out.println("\nItems Ordered:");
        
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int qty = quantities.get(i);
            double subtotal = product.getPrice() * qty;
            System.out.printf("  %s x%d = $%.2f\n", product.getName(), qty, subtotal);
        }
        
        System.out.printf("\nOrder Total: $%.2f\n", orderTotal);
        System.out.println("=====================");
    }
    
    @Override
    public String toString() {
        return "Order #" + orderID + " - " + customer.getName() + " - $" + String.format("%.2f", orderTotal) + " (" + status + ")";
    }
}
