package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerID;
    private String name;
    private String email;
    private List<Product> shoppingCart;
    private List<Integer> quantities; // Keeps track of how many of each product
    
    public Customer() {
        this.customerID = 0;
        this.name = "";
        this.email = "";
        this.shoppingCart = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }
    
    public Customer(int customerID, String name, String email) {
        setCustomerID(customerID);
        setName(name);
        setEmail(email);
        this.shoppingCart = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }
    
    // Getters
    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Product> getShoppingCart() { return shoppingCart; }
    public List<Integer> getQuantities() { return quantities; }
    
    // Setters with basic validation
    public void setCustomerID(int customerID) {
        if (customerID <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive.");
        }
        this.customerID = customerID;
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name can't be empty.");
        }
        if (name.trim().length() < 2) {
            throw new IllegalArgumentException("Name is too short.");
        }
        this.name = name.trim();
    }
    
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email address can't be empty.");
        }
        email = email.trim().toLowerCase();
        // Email validation
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Please enter a valid email address (needs an '@' symbol).");
        }
        this.email = email;
    }
    
    // Adds a product to the cart, or updates quantity if already there
    public void addToCart(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Can't add a null product to cart.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        
        // Check if product is already in cart
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (shoppingCart.get(i).getProductID() == product.getProductID()) {
                int currentQuantityInCart = quantities.get(i);
                int newTotalQuantity = currentQuantityInCart + quantity;
                
                if (!product.hasEnoughStock(newTotalQuantity)) {
                    throw new IllegalArgumentException("Adding " + quantity + " more would exceed available stock for " + product.getName() + ".");
                }
                quantities.set(i, newTotalQuantity);
                return; // Product quantity updated, so we're done
            }
        }
        
        // If product not found in cart, add it as a new item
        if (!product.hasEnoughStock(quantity)) {
            throw new IllegalArgumentException("Not enough stock for " + product.getName() + ". Only " + product.getStock() + " available.");
        }
        shoppingCart.add(product);
        quantities.add(quantity);
    }
    
    // Removes an item from the cart by its index (from display)
    public void removeFromCart(int productIndex) {
        if (productIndex < 0 || productIndex >= shoppingCart.size()) {
            throw new IllegalArgumentException("Invalid item number. Please choose from the list.");
        }
        shoppingCart.remove(productIndex);
        quantities.remove(productIndex);
    }
    
    // Updates the quantity of an item already in the cart
    public void updateQuantity(int productIndex, int newQuantity) {
        if (productIndex < 0 || productIndex >= shoppingCart.size()) {
            throw new IllegalArgumentException("Invalid item number. Please choose from the list.");
        }
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1. To remove, use the 'remove item' option.");
        }
        
        Product product = shoppingCart.get(productIndex);
        if (!product.hasEnoughStock(newQuantity)) {
            throw new IllegalArgumentException("Not enough stock for " + product.getName() + ". Only " + product.getStock() + " available.");
        }
        
        quantities.set(productIndex, newQuantity);
    }
    
    // Calculates the total cost of all items in the cart
    public double calculateTotal() {
        double total = 0.0;
        for (int i = 0; i < shoppingCart.size(); i++) {
            total += shoppingCart.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }
    
    // Checks if the cart is empty
    public boolean isCartEmpty() {
        return shoppingCart.isEmpty();
    }
    
    // Clears all items from the cart
    public void clearCart() {
        shoppingCart.clear();
        quantities.clear();
    }
    
    // Displays the current contents of the shopping cart
    public void displayCart() {
        if (isCartEmpty()) {
            System.out.println("Your shopping cart is empty.");
            return;
        }
        
        System.out.println("\n=== YOUR SHOPPING CART ===");
        for (int i = 0; i < shoppingCart.size(); i++) {
            Product product = shoppingCart.get(i);
            int qty = quantities.get(i);
            double subtotal = product.getPrice() * qty;
            System.out.printf("%d. %s x%d = $%.2f\n", (i + 1), product.getName(), qty, subtotal);
        }
        System.out.printf("\nTotal: $%.2f\n", calculateTotal());
        System.out.println("==========================");
    }
    
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}

