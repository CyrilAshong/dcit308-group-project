# Pharmacy Inventory Management System 

## 📌 Project Overview

A terminal-based Java application that simulates real-world inventory and sales management for a pharmacy (Atinka Meds). Built using core Java **only**—no external libraries or database systems.

---

## ⚙️ Features Implemented

* Drug Management (add, update, remove, list drugs)
* Drug Search & Sort (by name/code, using custom insertion sort)
* Supplier Mapping & Filtering
* Customer and Supplier Profiles
* Purchase Logging (Queue)
* Sales Logging (Stack)
* Stock Monitoring (PriorityQueue)
* Data persistence using file I/O

---

## 🧱 Technologies & Structures

* **Java Standard Library Only** (JDK 8–21 compatible)
* Data Structures: `HashMap`, `ArrayList`, `Queue`, `Stack`, `PriorityQueue`
* Sorting: Manual Insertion Sort
* Searching: Linear Search
* File Storage: Java Serialization (`ObjectOutputStream`, `ObjectInputStream`)

---

## 📁 Project Files

```
├── PharmacySystem.java              (main program)
├── Drug.java                        (drug entity)
├── Transaction.java                 (shared transaction object)
├── Supplier.java                    (supplier entity)
├── Customer.java                    (customer entity)
├── DrugSearchAndSort.java          (searching & sorting logic)
├── PurchaseHistory.java            (queue-based purchase tracker)
├── SalesLog.java                   (stack-based sales log)
├── StockMonitor.java               (min-heap for stock alerts)
```

---

## 💻 How to Compile & Run

1. Ensure all `.java` files are in the same folder.
2. Open terminal or command prompt.
3. Compile all files:

```bash
javac *.java
```

4. Run the main class:

```bash
java PharmacySystem
```

---

## 💡 Notes

* All input is handled through the console (no GUI).
* Data is automatically saved to files like `drugs.dat`, `purchases.dat`, and `sales.dat`.
* No external libraries (e.g., no Gson, no SQL, no JavaFX).
* Designed to work fully offline.

---

## 👥 Contributors (Team)

* Derrick (Profiles)
* Afful Nana Kwasi (Drug Management)
* Elorm Apaloo (Search & Sort)
* Cyril Ashong (Supplier Mapping)
* Kwesi A. Gyamenah (Purchase History)
* Theo Daniels (Sales Log)
* Chris (Stock Monitoring)

---

## 📄 License

This project is for academic purposes under the University of Ghana – DCIT308 course. Not intended for production use.
