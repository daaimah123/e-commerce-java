import com.ecommerce.Product;
import com.ecommerce.Customer;
import com.ecommerce.orders.Order;

import java.util.*;

public class ECommerceMain {
    private static List<Product> products = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Customer currentCustomer = null;
    private static int nextOrderID = 1001;
    
    public static void main(String[] args) {
        System.out.println("=== Welcome to Java E-Commerce System ===\n");
        
        try {
            initializeSystem();
            runMainMenu();
        } catch (Exception e) {
            // General exception for unexpected errors
            System.out.println("Oops! An unexpected error occurred: " + e.getMessage());
            System.out.println("Please try restarting the program.");
            // e.printStackTrace();
        } finally {
            scanner.close();
        }
        
        System.out.println("\nThanks for visiting our store!");
    }
    
    private static void initializeSystem() {
        try {
            // Old way of adding products (commented out to show iteration)
            // products.add(new Product(1, "Old Item", 10.0, "", 10));

            // Create sample art and craft products
            products.add(new Product(201, "Professional Acrylic Paint Set", 85.50, "Set of 24 vibrant, high-pigment acrylic paints (75ml tubes)", 10));
            products.add(new Product(202, "Artist's Easel (Wooden)", 150.00, "Adjustable solid beechwood easel for studio or outdoor use", 3));
            products.add(new Product(203, "Sketchbook (A4, 100 sheets)", 12.75, "Premium acid-free paper, ideal for pencils, charcoal, and ink", 50));
            products.add(new Product(204, "Watercolor Brush Set", 28.99, "Set of 12 synthetic brushes for watercolor painting", 25));
            products.add(new Product(205, "Clay Sculpting Tool Kit", 35.00, "11-piece set with various tools for pottery and sculpting", 18));
            products.add(new Product(206, "Calligraphy Pen Set", 45.99, "Includes 4 pens, 5 nibs, and black ink for elegant writing", 15));
            products.add(new Product(207, "Large Canvas (24x36 inch)", 22.00, "Primed cotton canvas, ready for oil or acrylic paints", 30));
            products.add(new Product(208, "Craft Glue (All-Purpose)", 5.99, "Strong, quick-drying adhesive for paper, fabric, and wood", 100));
            
            System.out.println("✓ Product catalog loaded successfully with art and craft supplies");
            
        } catch (Exception e) {
            // Error message for initialization
            throw new RuntimeException("Failed to set up the store's products: " + e.getMessage());
        }
    }
    
    private static void runMainMenu() {
        boolean running = true;
        while (running) {
            try {
                displayMainMenu();
                int choice = getMenuChoiceInput(1, 6); 
                
                switch (choice) {
                    case 1:
                        handleCustomerLogin();
                        break;
                    case 2:
                        if (currentCustomer != null) {
                            browseProducts();
                        } else {
                            System.out.println("Please log in first to browse products.");
                        }
                        break;
                    case 3:
                        if (currentCustomer != null) {
                            manageCart();
                        } else {
                            System.out.println("Please log in first to manage your cart.");
                        }
                        break;
                    case 4:
                        if (currentCustomer != null) {
                            checkout();
                        } else {
                            System.out.println("Please log in first to checkout.");
                        }
                        break;
                    case 5:
                        viewOrders();
                        break;
                    case 6:
                        System.out.println("Exiting the system...");
                        running = false; // Exit loop
                        break;
                    default:
                        // This case should ideally not be reached with getMenuChoiceInput
                        System.out.println("Invalid option. Please try again.");
                }
                
            } catch (Exception e) {
                // Error message for menu interaction
                System.out.println("Something went wrong with your choice: " + e.getMessage());
                System.out.println("Let's try that again.\n");
            }
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        if (currentCustomer != null) {
            System.out.println("Logged in as: " + currentCustomer.getName());
        } else {
            System.out.println("Not logged in.");
        }
        System.out.println("1. Customer Login/Register");
        System.out.println("2. Browse Products");
        System.out.println("3. View/Manage Cart");
        System.out.println("4. Checkout");
        System.out.println("5. View Orders");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void handleCustomerLogin() {
        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.println("1. Login as existing customer");
        System.out.println("2. Register as new customer");
        System.out.print("Choose an option: ");
        
        try {
            int choice = getMenuChoiceInput(1, 2);
            
            if (choice == 1) {
                loginExistingCustomer();
            } else if (choice == 2) {
                registerNewCustomer();
            } else {
                System.out.println("Invalid login option. Returning to main menu.");
            }
        } catch (Exception e) {
            System.out.println("Login process failed: " + e.getMessage());
        }
    }
    
    private static void loginExistingCustomer() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered yet. Please register first.");
            return;
        }
        
        System.out.println("\nAvailable customers:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getName()); // Simpler display
        }
        
        System.out.print("Select customer by number (1-" + customers.size() + "): ");
        try {
            int choice = getMenuChoiceInput(1, customers.size());
            if (choice != -1) { // Check for valid input from getMenuChoiceInput
                currentCustomer = customers.get(choice - 1);
                System.out.println("Welcome back, " + currentCustomer.getName() + "!");
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        } catch (Exception e) {
            // Error message
            System.out.println("Could not log in: " + e.getMessage());
        }
    }
    
    private static void registerNewCustomer() {
        System.out.println("\n=== REGISTER NEW CUSTOMER ===");
        
        try {
            System.out.print("Enter your full name: ");
            String name = scanner.nextLine(); 
            
            System.out.print("Enter your email address: ");
            String email = scanner.nextLine();
            
            // Basic validation before creating object
            if (name.trim().isEmpty() || email.trim().isEmpty()) {
                System.out.println("Name and email cannot be empty. Registration failed.");
                return;
            }
            
            int newCustomerID = customers.size() + 1001; // Simple ID generation
            Customer newCustomer = new Customer(newCustomerID, name, email); // Validation happens in Customer class
            customers.add(newCustomer);
            currentCustomer = newCustomer;
            
            System.out.println("Registration successful! Welcome, " + newCustomer.getName() + "!");
            
        } catch (IllegalArgumentException e) {
            // Error from Customer class validation
            System.out.println("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            // Exception for other unexpected errors during registration
            System.out.println("An error occurred during registration. Please try again.");
        }
    }
    
    private static void browseProducts() {
        boolean browsing = true;
        while (browsing) {
            System.out.println("\n=== BROWSE PRODUCTS ===");
            displayProducts();
            
            System.out.println("\nOptions:");
            System.out.println("1. Add product to cart");
            System.out.println("2. View product details");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = getMenuChoiceInput(1, 3);
                
                switch (choice) {
                    case 1:
                        addProductToCart();
                        break;
                    case 2:
                        viewProductDetails();
                        break;
                    case 3:
                        browsing = false; // Exit loop
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1, 2, or 3.");
                }
            } catch (Exception e) {
                System.out.println("Error during browsing: " + e.getMessage());
            }
        }
    }
    
    private static void displayProducts() {
        System.out.println("Available Products:");
        for (Product product : products) {
            product.displayDetails();
        }
    }
    
    private static void addProductToCart() {
        System.out.print("Enter product ID to add to cart: ");
        try {
            int productID = getIntegerInput(); 
            Product selectedProduct = findProductByID(productID);
            
            if (selectedProduct == null) {
                System.out.println("Product not found. Check the ID and try again.");
                return;
            }
            
            if (!selectedProduct.isAvailable()) {
                System.out.println("Sorry, " + selectedProduct.getName() + " is currently out of stock.");
                return;
            }
            
            System.out.print("Enter quantity (1-" + selectedProduct.getStock() + "): ");
            int quantity = getQuantityInput(1, selectedProduct.getStock()); 
            
            if (quantity != -1) { // Check if quantity input was valid
                currentCustomer.addToCart(selectedProduct, quantity);
                System.out.println("✓ Added " + quantity + " x " + selectedProduct.getName() + " to your cart!");
            } else {
                System.out.println("Invalid quantity. Item not added to cart.");
            }
            
        } catch (IllegalArgumentException e) {
            // Validation errors from Product/Customer classes
            System.out.println("Could not add to cart: " + e.getMessage());
        } catch (Exception e) {
            // General exception for other issues
            System.out.println("An error occurred while adding to cart. Please try again.");
        }
    }
    
    private static void viewProductDetails() {
        System.out.print("Enter product ID for details: ");
        try {
            int productID = getIntegerInput();
            Product product = findProductByID(productID);
            
            if (product == null) {
                System.out.println("Product not found with ID " + productID + ".");
                return;
            }
            
            System.out.println("\n=== PRODUCT DETAILS ===");
            System.out.println("Name: " + product.getName());
            System.out.println("Price: $" + String.format("%.2f", product.getPrice()));
            System.out.println("Description: " + product.getDescription());
            System.out.println("Stock: " + product.getStock() + " available");
            System.out.println("=======================");
            
        } catch (Exception e) {
            System.out.println("Error viewing product details: " + e.getMessage());
        }
    }
    
    private static void manageCart() {
        boolean managing = true;
        while (managing) {
            System.out.println("\n=== CART MANAGEMENT ===");
            currentCustomer.displayCart();
            
            if (currentCustomer.isCartEmpty()) {
                System.out.println("Your cart is empty. Time to go shopping!");
                return; // Exit cart management if empty
            }
            
            System.out.println("\nOptions:");
            System.out.println("1. Update item quantity");
            System.out.println("2. Remove item from cart");
            System.out.println("3. Clear entire cart");
            System.out.println("4. Return to main menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = getMenuChoiceInput(1, 4);
                
                switch (choice) {
                    case 1:
                        updateCartQuantity();
                        break;
                    case 2:
                        removeFromCart();
                        break;
                    case 3:
                        clearCart();
                        break;
                    case 4:
                        managing = false; // Exit loop
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1-4.");
                }
            } catch (Exception e) {
                System.out.println("Error managing cart: " + e.getMessage());
            }
        }
    }
    
    private static void updateCartQuantity() {
        System.out.print("Enter the item number from your cart to update: ");
        try {
            int itemIndex = getMenuChoiceInput(1, currentCustomer.getShoppingCart().size()) - 1;
            if (itemIndex == -2) { // getMenuChoiceInput returns -1 for invalid, -2 if out of range
                System.out.println("Invalid item number. Please try again.");
                return;
            }
            
            Product productInCart = currentCustomer.getShoppingCart().get(itemIndex);
            
            System.out.print("Enter new quantity for " + productInCart.getName() + " (1-" + productInCart.getStock() + "): ");
            int newQuantity = getQuantityInput(1, productInCart.getStock());
            
            if (newQuantity != -1) { // Check if quantity input was valid
                currentCustomer.updateQuantity(itemIndex, newQuantity);
                System.out.println("✓ Quantity updated successfully!");
            } else {
                System.out.println("Invalid quantity. Quantity not updated.");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Could not update quantity: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while updating cart quantity. Please try again.");
        }
    }
    
    private static void removeFromCart() {
        System.out.print("Enter the item number from your cart to remove: ");
        try {
            int itemIndex = getMenuChoiceInput(1, currentCustomer.getShoppingCart().size()) - 1;
            if (itemIndex == -2) { // getMenuChoiceInput returns -1 for invalid, -2 if out of range
                System.out.println("Invalid item number. Please try again.");
                return;
            }
            
            String productName = currentCustomer.getShoppingCart().get(itemIndex).getName();
            
            currentCustomer.removeFromCart(itemIndex);
            System.out.println("✓ Removed " + productName + " from your cart.");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Could not remove item: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while removing item. Please try again.");
        }
    }
    
    private static void clearCart() {
        System.out.print("Are you sure you want to clear your entire cart? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("yes") || confirmation.equals("y")) {
            currentCustomer.clearCart();
            System.out.println("✓ Your cart is now empty.");
        } else {
            System.out.println("Cart not cleared.");
        }
    }
    
    private static void checkout() {
        if (currentCustomer.isCartEmpty()) {
            System.out.println("Your cart is empty. Add some products before checking out.");
            return;
        }
        
        System.out.println("\n=== CHECKOUT ===");
        currentCustomer.displayCart();
        
        System.out.print("Ready to place your order? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (!confirmation.equals("yes") && !confirmation.equals("y")) {
            System.out.println("Checkout cancelled. You can continue shopping.");
            return;
        }
        
        try {
            // Create order
            Order order = new Order(nextOrderID++, currentCustomer);
            
            // Update product stock for each item in the order
            List<Product> itemsInOrder = order.getProducts();
            List<Integer> quantitiesInOrder = order.getQuantities();
            
            for (int i = 0; i < itemsInOrder.size(); i++) {
                itemsInOrder.get(i).reduceStock(quantitiesInOrder.get(i));
            }
            
            // Finalize order
            orders.add(order);
            currentCustomer.clearCart(); // Clear customer's cart after order
            order.setStatus("Confirmed"); // Set initial status
            
            System.out.println("\n✓ Your order has been placed successfully!");
            order.displayOrderSummary();
            
        } catch (IllegalArgumentException e) {
            // Validation errors from Order/Product classes
            System.out.println("Order placement failed: " + e.getMessage());
        } catch (Exception e) {
            // Exception for other issues during checkout
            System.out.println("An unexpected error occurred during checkout. Please try again.");
        }
    }
    
    private static void viewOrders() {
        System.out.println("\n=== ALL ORDERS ===");
        if (orders.isEmpty()) {
            System.out.println("No orders have been placed yet.");
            return;
        }
        
        for (Order order : orders) {
            System.out.println(order);
        }
        int origOrderId = 0;
        int orderID = getIntegerInput();
        Order order = findOrderByID(orderID);
        System.out.print("\nEnter an Order ID to see details (or 0 to go back): ");
        try {
            origOrderId = orderID;
            if (orderID == 0) return;
            

            if (order != null) {
                order.displayOrderSummary();
            } else {
                System.out.println("Order with ID " + orderID + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error viewing order details: " + e.getMessage());
        }

        if (order == null) {
            System.out.println("⚠️ Internal error: Order reference is null.");
            return;
        }
        System.out.print("\nWould you like to update the status of this order? (yes/no): ");
        String updateChoice = scanner.nextLine().trim().toLowerCase();
        if (updateChoice.equals("yes") || updateChoice.equals("y")) {
            List<String> validStatuses = Arrays.asList("Pending", "Confirmed", "Processing", "Shipped", "Delivered", "Cancelled");
            //"Pending", "Confirmed", "Processing", "Shipped", "Delivered", "Cancelled"

            System.out.println("Available Status Options:");
            for (int i = 0; i < validStatuses.size(); i++) {
                System.out.println((i + 1) + ". " + validStatuses.get(i));
            }

            System.out.print("Enter the number for new status: ");
            int statusChoice = getMenuChoiceInput(1, validStatuses.size());

            if (statusChoice >= 1 && statusChoice <= validStatuses.size()) {
                String newStatus = validStatuses.get(statusChoice - 1);
                try {
                    Objects.requireNonNull(order).setStatus(newStatus); // Assuming this internally validates transitions
                    System.out.println("✅ Order status updated to: " + newStatus);
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Failed to update status: " + e.getMessage());
                }
            } else {
                System.out.println("❌ Invalid status choice. No changes made.");
            }
        }

    }
    
    
    // Gets a menu choice, returns -1 for invalid number, -2 for out of range
    private static int getMenuChoiceInput(int min, int max) {
        String input = scanner.nextLine().trim();
        try {
            int choice = Integer.parseInt(input);
            if (choice >= min && choice <= max) {
                return choice;
            } else {
                System.out.print("That's not a valid option. Please enter a number between " + min + " and " + max + ": ");
                return -2; // Indicate out of range
            }
        } catch (NumberFormatException e) {
            System.out.print("Please enter a number, not text. Try again: ");
            return -1; // Indicate invalid format
        }
    }
    
    // Gets a general integer input, returns -1 for invalid number
    private static int getIntegerInput() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.print("Please enter a valid number: ");
            return -1; // Indicate invalid format
        }
    }
    
    // Gets a quantity input, returns -1 for invalid number or out of range
    private static int getQuantityInput(int min, int max) {
        String input = scanner.nextLine().trim();
        try {
            int quantity = Integer.parseInt(input);
            if (quantity >= min && quantity <= max) {
                return quantity;
            } else {
                System.out.print("Quantity must be between " + min + " and " + max + ". Try again: ");
                return -1; // Indicate out of range
            }
        } catch (NumberFormatException e) {
            System.out.print("Please enter a number for quantity: ");
            return -1; // Indicate invalid format
        }
    }
    
    // Helper to find product by ID
    private static Product findProductByID(int productID) {
        for (Product product : products) {
            if (product.getProductID() == productID) {
                return product;
            }
        }
        return null; // Not found
    }
    
    // Helper to find order by ID
    private static Order findOrderByID(int orderID) {
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null; // Not found
    }
    // TODO: Consider adding a way to search products by name
    // TODO: Maybe add a simple admin menu for managing stock
}
