# 📦 Huffman Encoding & Decoding in Java (2021)

This project implements **Huffman Coding** — a data compression technique — in Java. It was developed as part of the **Data Structures and Operating Systems** module in 2021.

The application performs both **encoding** (compression) and **decoding** (decompression) of strings using a custom-built Huffman tree, simulating real-world compression techniques.

---

## 📌 Key Features

- 📊 Builds a frequency table from input data
- 🌳 Constructs a Huffman tree using custom `Leaf` and `Branch` classes
- 🔁 Traverses the tree to generate binary codes for characters
- 📥 Encodes string data into a compressed format
- 📤 Decodes compressed data back to original input
- 🧠 Analyzes time complexity of each function (e.g., O(n), O(n²))

---

## ⚙️ Methods Overview

### 🔐 Encode

- `FreqTable()` – Generates frequency of characters (O(n))  
- `treeFromFreqTable()` – Builds Huffman tree with priority queue (O(n²))  
- `traverse()` – Recursively assigns binary codes to characters  
- `buildcode()` – Builds final encoding map  
- `encode()` – Integrates all above methods to compress input  

### 🔓 Decode

- `treeFromCode()` – Reconstructs Huffman tree from stored codes (O(n²))  
- `decode()` – Uses the tree to decode the binary stream into original text  

---

## 💡 Data Structures Used

- **HashMap** – Stores character-code mappings  
- **Priority Queue** – Used to construct optimal Huffman tree  
- **Custom Tree** – Built using `Leaf` and `Branch` nodes  
- **Boolean Lists** – Represent binary paths for each character  

---

## 📚 Theoretical Background

### 📘 Huffman Coding
A greedy algorithm that assigns shorter binary codes to more frequent characters. It results in efficient lossless data compression.

### 🧱 Additional Examples
The report also discusses how data structures and algorithms like:
- **Buddy System** (memory management)
- **Hash Tables** (page tables, file systems)

are used in operating systems for efficient data handling.

---

## 🧪 Complexity Analysis

| Method            | Time Complexity |
|-------------------|-----------------|
| Frequency Table   | O(n)            |
| Tree Construction | O(n²)           |
| Tree Traversal    | O(n)            |
| Encoding/Decoding | O(n), O(n²)     |

---

## 📄 References

- Tanenbaum, *Modern Operating Systems*  
- Doeppner, *Operating Systems in Depth (2010)*  
- GeeksForGeeks, *Hashed Page Tables in Operating System (2020)*  

---

## 👨‍💻 Developed By

**Vickshan Vicknakumaran**  
University of Brighton  
Data Structures & Operating Systems | 2021

---
