package com.ecommerce;

public class Product {
    private int productID;
    private String name;
    private double price;
    private String description;
    private int stock;
    
    // Constructor to create a new product
    public Product() {
        this.productID = 0;
        this.name = "";
        this.price = 0.0;
        this.description = "";
        this.stock = 0;
    }
    
    // Constructor with all product details
    public Product(int productID, String name, double price, String description, int stock) {
        setProductID(productID);
        setName(name);
        setPrice(price);
        setDescription(description);
        setStock(stock);
    }
    
    // Getters
    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getStock() { return stock; }
    
    // Setters with basic validation
    public void setProductID(int productID) {
        if (productID <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number.");
        }
        this.productID = productID;
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name can't be empty.");
        }
        if (name.trim().length() > 100) { 
            throw new IllegalArgumentException("Product name is too long.");
        }
        this.name = name.trim();
    }
    
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price can't be negative.");
        }
        this.price = price;
    }
    
    public void setDescription(String description) {
        if (description == null) {
            this.description = "";
        } else if (description.length() > 250) {
            throw new IllegalArgumentException("Description is too long.");
        } else {
            this.description = description.trim();
        }
    }
    
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock quantity can't be negative.");
        }
        this.stock = stock;
    }
    
    // Check if product is in stock
    public boolean isAvailable() {
        return stock > 0;
    }
    
    // Check if there's enough stock for a requested quantity
    public boolean hasEnoughStock(int requestedQuantity) {
        return stock >= requestedQuantity;
    }
    
    // Reduce stock after a purchase
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Can't reduce stock by zero or less.");
        }
        if (quantity > stock) {
            throw new IllegalArgumentException("Not enough stock for " + name + ". Only " + stock + " available.");
        }
        stock -= quantity;
    }
    
    // Prints product details for browsing
    public void displayDetails() {
        String availability = isAvailable() ? "In Stock (" + stock + " available)" : "Out of Stock";
        System.out.printf("%d. %s - $%.2f\n   %s\n   %s\n", 
                           productID, name, price, description, availability);
    }
    
    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
}
