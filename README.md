# Simple Java E-Commerce System

This project is a console-based Java application that simulates a basic e-commerce system for an online art and craft supply store. It allows customers to browse products, add them to a shopping cart, and place orders. The system emphasizes good code organization using Java packages and provides an interactive command-line interface.

## Description

This application models a small online shop where users can interact with a catalog of art and craft supplies. It features core e-commerce functionalities such as:

* **Product Management:** Products have IDs, names, prices, descriptions, and stock quantities.
* **Customer Management:** Customers have IDs, names, and email addresses, and each has their own shopping cart.
* **Shopping Cart Operations:** Customers can add products, update quantities, and remove items from their cart.
* **Order Processing:** Customers can place orders from their cart, which updates product stock and generates an order summary.
* **Order Viewing:** All placed orders can be viewed, along with their details.

The project is structured using Java packages (`com.ecommerce` and `com.ecommerce.orders`) to ensure proper encapsulation and modularity, making the codebase clean and easy to understand.

## Purpose

The primary purpose of this project is to demonstrate fundamental Java programming concepts in a practical, real-world scenario. Key learning objectives include:

* **Object-Oriented Programming (OOP):** Implementing classes (`Product`, `Customer`, `Order`) with attributes, constructors, getters, setters, and methods.
* **Encapsulation:** Using appropriate access modifiers (`private`) and providing controlled access through public methods.
* **Package Organization:** Structuring code into logical packages (`com.ecommerce`, `com.ecommerce.orders`) for better maintainability and to avoid naming conflicts.
* **Input Validation & Error Handling:** Implementing robust checks for user input and handling potential exceptions gracefully with user-friendly messages.
* **Interactive Console Application:** Building a command-line interface that guides the user through various operations.

## How the Project Works

The application runs as a console program. When launched, it initializes a catalog of art and craft products. Users are then presented with a main menu:

1. **Customer Login/Register:** Users can either log in as an existing customer (if any are registered) or create a new customer account. This step is necessary to access shopping features.
2. **Browse Products:** Displays all available products with their details. From here, users can choose to add products to their cart or view more specific details about an item.
3. **View/Manage Cart:** Shows the current contents of the logged-in customer's shopping cart. Users can update quantities of items, remove items, or clear their entire cart.
4. **Checkout:** Initiates the order placement process. If the cart is not empty, it confirms the order, updates product stock, clears the cart, and generates an order summary.
5. **View Orders:** Displays a list of all orders placed in the system. Users can select an order by its ID to view a detailed summary.
6. **Exit:** Closes the application.

The system uses basic input validation to guide the user and prevent common errors, providing helpful messages when invalid input is detected.

## How to Run It

To run this project locally, you'll need a Java Development Kit (JDK) installed on your system (version 8 or higher is recommended).

### **Step 1: Set Up Project Files**

Create a main project folder (e.g., `ECommerceProject`). Inside this folder, create the following directory structure and place the corresponding `.java` files:

```
ECommerceProject/
├── com/
│   └── ecommerce/
│       ├── Product.java
│       ├── Customer.java
│       └── orders/
│           └── Order.java
└── ECommerceMain.java
```

* **`Product.java`**: Go into `ECommerceProject/com/ecommerce/` and create this file.
* **`Customer.java`**: Go into `ECommerceProject/com/ecommerce/` and create this file.
* **`Order.java`**: Go into `ECommerceProject/com/ecommerce/orders/` and create this file.
* **`ECommerceMain.java`**: Create this file directly inside the `ECommerceProject/` folder.

Copy the code for each class into its respective file.

### **Step 2: Compile the Java Files**

Open your terminal or command prompt. Navigate to the `ECommerceProject` directory (the root folder containing `com` and `ECommerceMain.java`).

Run the following command to compile all Java files:

```bash
javac -d . com/ecommerce/*.java com/ecommerce/orders/*.java ECommerceMain.java
```

The `-d .` flag tells the Java compiler to place the compiled `.class` files in the current directory, maintaining the package structure.

### **Step 3: Run the Application**

After successful compilation, run the main application using this command from the `ECommerceProject` directory:

```shellscript
java ECommerceMain
```

The program will start, display the main menu, and you can begin interacting with the e-commerce system!

### **Troubleshooting**

- **`javac` or `java` command not found:** Ensure Java JDK is installed and its `bin` directory is added to your system's PATH environment variable.
- **Compilation errors:** Double-check that all code has been copied correctly and that the file paths match the package declarations exactly.
- **`Could not find or load main class ECommerceMain`:** This usually means compilation failed, or you are not running the command from the correct directory (`ECommerceProject`).

Feel free to explore the code and enhance it with more features!
