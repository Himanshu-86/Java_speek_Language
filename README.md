#  SPEEK Interpreter

A custom programming language interpreter built in Java as part of an Advanced Object-Oriented Programming project.

SPEEK (**Simple Plain English Execution Kit**) is a lightweight scripting language that uses English-like syntax to perform variable assignments, arithmetic operations, conditional execution, and loops.

---

##  Project Objectives

The primary goal of this project is to understand how programming languages work internally by implementing the core components of an interpreter from scratch.

This project demonstrates:

- Lexical Analysis (Tokenization)
- Syntax Analysis (Parsing)
- Abstract Syntax Trees (AST)
- Runtime Execution
- Variable Storage and Management
- Object-Oriented Design Principles

---

##  Features

###  Variable Assignment

```speek
let x be 10
let y be 20
```

###  Arithmetic Expressions

Supported operators:

```text
+
-
*
/
```

Example:

```speek
let result be x + y * 2
```

###  Output Statements

```speek
say result
say "Hello World"
```

###  Conditional Statements

```speek
if x is greater than y then
say "x is larger"
```

###  Repeat Loops

```speek
repeat 3 times
say "Hello"
```

###  Runtime Environment

Variables are stored and managed through a shared Environment object during execution.

---

#  System Architecture

```text
Source Code
     │
     ▼
Tokenizer
(Lexical Analysis)
     │
     ▼
Token Stream
     │
     ▼
Parser
(Syntax Analysis)
     │
     ▼
Abstract Syntax Tree (AST)
     │
     ▼
Interpreter
(Execution Engine)
     │
     ▼
Console Output
```

---

#  Interpreter Workflow

## Step 1: Tokenization

The Tokenizer reads source code character-by-character and converts it into tokens.

Example:

```speek
let x be 10
```

Generated Tokens:

```text
LET
IDENTIFIER(x)
BE
NUMBER(10)
```

Responsibilities:

- Recognize keywords
- Identify identifiers
- Read numbers
- Read strings
- Handle operators
- Process comparisons

---

## Step 2: Parsing

The Parser converts tokens into instructions and expression trees.

Example:

```speek
let result be x + y * 2
```

AST Representation:

```text
        +
       / \
      x   *
         / \
        y   2
```

This structure automatically enforces operator precedence.

---

## Step 3: Execution

The Interpreter executes instructions sequentially.

Example:

```speek
let x be 10
let y be 5
let result be x + y * 2
say result
```

Execution:

```text
x = 10
y = 5
result = 20
print 20
```

Output:

```text
20
```

---

#  Project Structure

```text
.
├── Main.java
│
├── execution/
│   ├── Interpreter.java
│   ├── Environment.java
│   ├── Instruction.java
│   ├── AssignInstruction.java
│   ├── PrintInstruction.java
│   ├── IfInstruction.java
│   └── RepeatInstruction.java
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

#  SPEEK Language Syntax

## Variable Assignment

```speek
let age be 18
```

## Printing

```speek
say age
say "Hello"
```

## Arithmetic

```speek
let total be x + y
let product be x * y
```

## Conditional

```speek
if score is greater than 50 then
say "Pass"
```

## Loop

```speek
repeat 5 times
say "Running"
```

---

#  How to Run

## Compile

### Windows PowerShell

```powershell
javac -d . (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

### Command Prompt

```cmd
javac Main.java tokenizer\*.java parser\*.java execution\*.java
```

---

## Run

```cmd
java Main test.txt
```

---

#  Sample Program

### Input

```speek
let x be 10
let y be 5

let sum be x + y

say "Sum:"
say sum

if x is greater than y then
say "x is greater than y"

repeat 3 times
say "Loop Running"
```

### Output

```text
Sum:
15

x is greater than y

Loop Running
Loop Running
Loop Running
```

---

#  Technologies Used

- Java
- Object-Oriented Programming
- Collections Framework
- Abstract Syntax Trees (AST)
- Custom Language Design

---

#  Concepts Demonstrated

- Lexical Analysis
- Parsing
- AST Construction
- Tree-Based Expression Evaluation
- Runtime Environment
- Interpreter Design Pattern
- Encapsulation
- Polymorphism
- Abstraction
- Modular Architecture

---

#  Team Contribution

The project was divided into three major modules:

### Tokenizer Module
- Token generation
- Keyword recognition
- Lexical analysis

### Parser Module
- AST construction
- Expression parsing
- Instruction generation

### Execution Module
- Runtime environment
- Instruction execution
- Program interpretation

This modular structure enabled parallel development and clear separation of concerns.

---

#  Future Enhancements

- Nested loops
- Nested conditionals
- Equality operators (`==`)
- Else blocks
- Better error reporting with line numbers
- Parentheses support
- User-defined functions

---

#  Academic Context

Developed as part of the **Advanced Object-Oriented Programming** course at **Sitare University**.

The project demonstrates how a programming language interpreter works internally using object-oriented design and software engineering principles.

---

##  Authors

- Himanshu Kumar
- Team Members

**Language:** Java  
**Project Type:** Custom Programming Language Interpreter (SPEEK)
