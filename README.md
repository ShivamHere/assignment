# 🚚 Delivery Optimization System (Spring Boot)

This is a modular, extensible Spring Boot application that simulates a real-world delivery optimization system. It uses a graph-based approach with a modified Dijkstra algorithm to compute the most efficient delivery paths considering travel time and restaurant preparation delays.

---

## 🧩 Project Features

- **User, Restaurant, and Delivery Agent Management**
- **Order Placement (Single and Batch)**
- **Greedy Delivery Agent Allocation**
- **Graph-Based Path Optimization with Waiting Time Consideration**
- **Modular Codebase** (Separation of concerns using services, controllers, and models)

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.x
- Maven
- RESTful APIs
- In-Memory Data Store (for simplicity)

---

## 🧪 Quickstart

### 🔧 Prerequisites
- Java 21
- Maven

### 🚀 Run the App
```bash
mvn clean install
mvn spring-boot:run
```
The server will start at: `http://localhost:8080`

## 📦 API Reference

### 1. 👥 User APIs
| Endpoint         | Method | Description        |
|------------------|--------|--------------------|
| `/users/addUser` | POST   | Add a user         |
| `/users/{id}`    | GET    | Get user by ID     |

### 2. 🍽️ Restaurant APIs
| Endpoint                     | Method | Description             |
|------------------------------|--------|-------------------------|
| `/restaurants/addRestaurant` | POST   | Add a restaurant        |
| `/restaurants/{id}`          | GET    | Get restaurant by ID    |

### 3. 🚴 Delivery Agent APIs
| Endpoint                               | Method | Description                      |
|----------------------------------------|--------|----------------------------------|
| `/deliveryAgents/addDeliveryAgent`     | POST   | Add a delivery agent             |
| `/deliveryAgents/{id}`                 | GET    | Get delivery agent by ID         |

### 4. 📦 Order APIs
| Endpoint                      | Method | Description                              |
|-------------------------------|--------|------------------------------------------|
| `/orders/addOrder`            | POST   | Add a single order                       |
| `/orders/addBatchOrders`      | POST   | Add a batch of orders                    |

### 5. 📈 Delivery Optimization APIs
| Endpoint                           | Method | Description                                                                      |
|------------------------------------|--------|----------------------------------------------------------------------------------|
| `/delivery/getBestDeliveryAgent`   | POST   | Get the closest delivery agent for a list of orders (greedy strategy)            |
| `/delivery/getBestPath`            | POST   | Get best route and total time for a delivery agent across multiple orders        |

## 🧠 Delivery Path Optimization Logic

This uses a **graph-based algorithm** to determine the best delivery sequence.

### Highlights:

- **Travel time** is calculated using the Haversine formula distance approximation.
- **Waiting time** for restaurant preparation is added only **if** the agent reaches before preparation is done.
- Ensures the agent visits **restaurants before users** (delivery constraint).
- Modified Dijkstra logic is used to select the next optimal node using a `PriorityQueue`.

### 🔁 Simplified Flow:

1. Start from the **delivery agent's location**.
2. Use **PriorityQueue** to always choose the next stop with the least cumulative time.
3. While moving toward a restaurant:
   - Estimate **travel time**.
   - If agent arrives **before order is ready**, wait for remaining time.
4. A user’s location is only visited **after** their order has been picked up from the restaurant.
5. Repeat until all orders are delivered.

### ✅ Constraint Handling:

- The algorithm prevents visiting a user before their respective restaurant.
- Restaurant preparation time dynamically reduces during travel time toward it.

## 📬 Postman Collection

You can test all APIs using this [Postman Collection](.src/main/resources/LucidityAssignment.postman_collection.json)
