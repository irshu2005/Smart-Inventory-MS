# Smart-Inventory-MS

This is our first Project - Smart Inventory Management System

## Overview

Smart Inventory Management System is designed to efficiently track and manage stock, suppliers, and inventory operations. The system helps businesses streamline inventory processes, reduce errors, and improve productivity.

## Features

- Product and stock tracking
- Supplier management
- Inventory operations (add, update, delete)
- Reporting and analytics
- User authentication and authorization
- Intuitive dashboard and UI

## Technology Stack

- **Programming Language:** Java
- **Framework:** Hibernate (for ORM and database operations)
- **UI:** Java Swing
- **Database:** MySQL
- **Application Server:** Apache Tomcat

## Architecture

- **Java Swing** is used for building the graphical user interface (desktop application).
- **Hibernate** provides object-relational mapping between Java classes and MySQL tables.
- **MySQL** is used as the backend database.
- **Apache Tomcat** hosts the application (if any web modules are present).

## Installation

### Prerequisites

- Java JDK (8 or above)
- Apache Tomcat Server
- MySQL Server
- [Optional] IDE such as Eclipse or IntelliJ IDEA

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/irshu2005/Smart-Inventory-MS.git
   ```

2. **Set up the MySQL Database**
   - Install MySQL and start the server.
   - Create a database for the inventory system, e.g., `inventory_db`.
   - Import the provided SQL schema (if available) or create tables as required by the application.

3. **Configure Hibernate**
   - Edit the `hibernate.cfg.xml` file with your MySQL configuration (username, password, database name).

4. **Build the Project**
   - Open the project in your IDE.
   - Build the project and resolve any dependencies (add Hibernate and MySQL connector JARs if needed).

5. **Deploy to Apache Tomcat**
   - Package the application as a `.war` file if it has a web module.
   - Deploy the `.war` file to the `webapps` directory of your Tomcat installation.
   - Start the Tomcat server.

6. **Run the Application**
   - For desktop (Swing) modules, run the main class from your IDE or command line.
   - For web modules, access the application at `http://localhost:8080/Smart-Inventory-MS`

## Usage

- Access the dashboard through the Swing application.
- Add and manage products and suppliers.
- Track inventory movements and generate reports.

## Contributing

We welcome contributions! Please fork the repository and open a pull request with your proposed changes.

## License

[Specify License, e.g., MIT License]

## Authors

- irshu2005

---

*For more details, explore the codebase and documentation within the repository.*
