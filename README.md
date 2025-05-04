# 💳 E-Wallet Application

A microservices-based digital wallet system designed to facilitate secure and efficient financial transactions. The application is built using **Java** and **Spring Boot**, adhering to a modular architecture that ensures scalability and maintainability.

---

## 🧾 Overview

This E-Wallet Application enables users to:

- 👤 Create and manage user accounts.
- 💰 Perform wallet transactions such as deposits, withdrawals, and transfers.
- 🔐 Handle user authentication and authorization.
- 📩 Receive notifications related to account activities.

### 🧩 Microservices Architecture

The system is divided into the following microservices:

- **User Service** – Manages user registration, authentication, and profile management.
- **Wallet Service** – Handles wallet creation and balance management.
- **Transaction Service** – Processes transactions between wallets.
- **Notification Service** – Sends notifications to users about their account activities.

---

## 🛠️ Technologies Used

- ☕ Java 17  
- 🌱 Spring Boot  
- ☁️ Spring Cloud (for microservices communication)  
- 🗃️ Spring Data JPA  
- 🐬 MySQL  
- 🔧 Maven  
- 🐳 Docker *(optional, for containerization)*  

---

## 🚀 Getting Started

Follow these steps to set up the application locally.

## 🔧 Running the Application Locally

Follow these steps to set up and run the E-Wallet Application on your local machine:

- 📥 **Clone the repository**

  ```bash
  git clone https://github.com/deepanshu-hub/e-wallet-application.git
  cd e-wallet-application
### 🐘 Download and Set Up Kafka

- 📥 **Download Kafka**  
  Download Kafka from the official website:  
  [https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)
  Refer to this setup video (up to 6:56 minutes): https://youtu.be/BwYFuhVhshI?si=dHsy3sMioNhoYYwI

- ⚙️ **Start Zookeeper and Kafka Broker**

  Open a terminal and navigate to the Kafka directory, then run the following commands:

  ```bash
  # Start Zookeeper server
  bin/zookeeper-server-start.sh config/zookeeper.properties

  # In a new terminal, start Kafka broker
  bin/kafka-server-start.sh config/server.properties


### 🔄 Run All Microservices

  For each service (`user-service`, `wallet-service`, `transaction-service`, `notification-service`)

### 📌 Future Enhancements

- 🔄 Implement API Gateway using **Spring Cloud Gateway**
- 📡 Add **Service Discovery** using **Eureka**
- 💳 Integrate **External Payment Providers**
- 🌐 Build a frontend using **React** or **Angular**
- 🛡️ Add **OAuth2** & **JWT-based Security**



