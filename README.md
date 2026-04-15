# 🧠 Speek Interpreter

A lightweight **custom programming language interpreter** built in Java.  
It demonstrates core **compiler design concepts**: lexical analysis, parsing (AST), and interpretation.

---

##  Highlights

-  Custom language: *Speek*
-  Arithmetic: `+ - * /`
-  Variables & assignments
-  `print` statements
-   Conditionals: `if`
-   Loops: `repeat`
-   Clean modular architecture (Tokenizer → Parser → Interpreter)

---

##  Architecture

```
Source Code (test.txt)
        ↓
Tokenizer (Lexical Analysis)
        ↓
Parser (AST)
        ↓
Interpreter (Execution Engine)
        ↓
Output (Console)
```

---

##  Project Structure

```
.
├── Main.java
├── execution/
│   ├── Interpreter.java
│   ├── Instruction.java
│   ├── AssignInstruction.java
│   ├── PrintInstruction.java
│   ├── IfInstruction.java
│   ├── RepeatInstruction.java
│   └── Environment.java
│
├── parser/
│   ├── Parser.java
│   ├── Expression.java
│   ├── BinaryOpNode.java
│   ├── NumberNode.java
│   ├── StringNode.java
│   └── VariableNode.java
│
├── tokenizer/
│   ├── Tokenizer.java
│   ├── Token.java
│   └── TokenType.java
│
└── test.txt
```

---

##  How to Run

### 1️⃣ Compile all Java files (IMPORTANT for first run)
javac -d . (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

### 2️⃣ Run

```
java Main test.txt
```

---

##  Example

### Input (`test.txt`)
```
x = 5 + 3
print x
```

### Output
```
8
```

---

##  Key Concepts

- Object-Oriented Programming (OOP)
- Abstract Syntax Tree (AST)
- Interpreter Design Pattern
- Compiler Fundamentals

---

##  Notes

- Ensure package names match folder structure.
- Always compile all `.java` files before running.
