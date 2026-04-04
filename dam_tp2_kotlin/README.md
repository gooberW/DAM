# Assignment 2 - Weather App
**Course:** LEIM

**Student:** A51692

**Date:** April 15th, 2026

**Repository URL:** https://github.com/gooberW/DAM

---

## Table of Contents

- [Assignment 2 - Weather App](#assignment-2---weather-app)
  - [Table of Contents](#table-of-contents)
  - [1. Introduction](#1-introduction)
  - [2. System Overview](#2-system-overview)
  - [3. Architecture and Design](#3-architecture-and-design)
  - [4. Implementation](#4-implementation)
    - [4.1. Event Log Processing](#41-event-log-processing)
    - [4.2. In-Memory Cache](#42-in-memory-cache)
    - [4.3. Configurable Data Pipeline](#43-configurable-data-pipeline)
    - [4.4. 2D Vector Library](#44-2d-vector-library)
      - [Arithmetic Operators](#arithmetic-operators)
      - [Magnitude, Dot Product and Normalization](#magnitude-dot-product-and-normalization)
      - [Index Operator](#index-operator)
      - [Comparison Operator](#comparison-operator)
    - [Challenge](#challenge)
    - [4.5. Cool Weather App (Android)](#45-cool-weather-app-android)
  - [5. Testing and Validation](#5-testing-and-validation)
  - [6. Usage Instructions](#6-usage-instructions)
- [Development Process](#development-process)
  - [7. Version Control and Commit History](#7-version-control-and-commit-history)
  - [8. Difficulties and Lessons Learned](#8-difficulties-and-lessons-learned)
  - [9. Future Improvements](#9-future-improvements)
  - [10. AI Usage Disclosure (Mandatory)](#10-ai-usage-disclosure-mandatory)

---

## 1. Introduction
This assignment provides a continuation to the Kotlin programming language and 
the Android development environment. The objective was to explore different 
concepts of the Kotlin language and...

## 2. System Overview
The project is divided into individual exercises, to demonstrate specific programming 
and development concepts:

- **Exercise 1.1:** Sealed classes, extension functions and high-order functions.

- **Exercise 1.2:** Generic in-memory cache.
  - **Challenge:**

- **Exercise 1.3:** Data processing pipeline in Kotlin.
    - **Challenge:**
  
- **Exercise 1.4:** Mathematical 2D vector.
    - **Challenge:**
  
- **Android - The Cool Weather App:** 

## 3. Architecture and Design
A modular package structure was used to keep exercises isolated and maintainable. This ensures that the namespace for each exercise remains clean.

> **Diagram 1.** Directory diagram of Kotlin exercises project structure.
```
dam/
├── exer_1_1/
│   └── Event.kt
│   └── Main.kt
├── exer_1_2/
│   └── Cache.kt
│   └── Main.kt
├── exer_1_3/
│   └── Pipeline.kt
│   └── Main.kt
└── exer_1_4/
    ├── Vec2.kt
    └── Main.kt
```

## 4. Implementation
### 4.1. Event Log Processing

> **Listing 1.** .
``` 

```

### 4.2. In-Memory Cache


### 4.3. Configurable Data Pipeline

> **Listing 2.** .
``` 

```

### 4.4. 2D Vector Library

This exercise explores operator overloading in Kotlin, a feature that allows
standard operators (`+`, `-`, `*`, `<`, *etc.*) to be defined for custom types by
marking functions with the `operator` keyword and giving them reserved names such
as `plus`, `minus`, or `times`. The compiler then maps expressions like `a + b`
to `a.plus(b)`, making custom types feel as natural as built-in primitives.

`Vec2` is declared as a `data class`, which automatically generates `toString()`,
`equals()`, and `hashCode()` based on its properties $x$ and $y$.

#### Arithmetic Operators

The `+`, `-`, and `*` operators produce a new `Vec2` instance, leaving operands
unchanged. The `*` operator accepts a `Double` scalar rather than another `Vec2`,
scaling both components uniformly, and the unary `-` negates both components,
reversing the vector's direction, seen on the Listing X below.

>Listing X. Arithemtic operation examples.
```
operator fun times(multiplier: Double): Vec2 {
  return Vec2(x * multiplier, y * multiplier)
}

operator fun unaryMinus(): Vec2 {
  return Vec2(-x, -y);
}
```

#### Magnitude, Dot Product and Normalization

The magnitude computes the Euclidean length $\sqrt{x^2 + y^2}$, and the dot product
$x_1 \cdot x_2 + y_1 \cdot y_2$ measures how much two vectors align. Normalization
divides each component by the magnitude, producing a unit vector of length 1.
Since dividing by zero is undefined, normalizing a zero vector throws an
`IllegalStateException`.

> Listing Y. Normalization function.
```
fun normalized(): Vec2 {
  if (magnitude() == 0.0) {
    throw IllegalStateException("Cannot normalize a zero vector")
  }
  
  return Vec2(x / magnitude(), y / magnitude())
}
```

#### Index Operator

The `get` operator enables bracket notation (`a[0]`, `a[1]`), mapping index `0`
to `x` and `1` to `y`. Any other index throws an `IndexOutOfBoundsException` 
with an error message, seen in the Listing Z below.

> Listing Z. Implementation of the ``get`` operator.
```
operator fun get(index: Int): Double = when (index) {
  0 -> x
  1 -> y
  else -> throw IndexOutOfBoundsException("Index $index out of bounds, Vec2 only has indices 0 and 1")
}
```

#### Comparison Operator

`Vec2` implements `Comparable<Vec2>`, overriding `compareTo` to order vectors by
magnitude. This unlocks relational operators (`<`, `>`, `<=`, `>=`) as well as
standard library functions such as `max()` and `sorted()` on collections
of `Vec2`.

### Challenge

Left-hand scalar multiplication (`2.0 * v`) cannot be defined as a member
function on `Vec2` because the left-hand operand is `Double`, a type we don't
control. Kotlin solves this with extension functions. By defining
`operator fun Double.times(v: Vec2)` outside the class, the compiler maps
`2.0 * v` to this function, making scalar multiplication commutative.

Destructuring allows a `Vec2` to be unpacked into individual variables with
`val (x, y) = a`. Since `Vec2` is a `data class`, the `component1()` and
`component2()` operator functions are generated automatically for each property
in declaration order, so no additional code is required.

### 4.5. Cool Weather App (Android)



## 5. Testing and Validation
**Testing Strategy:** Functional testing was performed by running the console applications in IntelliJ and deploying the Android apps to the Pixel 9 Pro AVD.

**Edge Cases:** Validation against non-numeric input and division by zero was implemented for the Calculator.

**Virtual Library:** Borrowing logic was validated against out-of-stock scenarios, and negative copy counts were prevented via setters.

**Logcat:** The Logcat monitor in Android Studio was used to verify lifecycle messages (onCreate) and debug variable states.

## 6. Usage Instructions
1. **Requirements:** Android Studio, IntelliJ IDEA, and Kotlin SDK.

2. **Execution (Kotlin):** Open the ``dam_tp2_kotlin`` project in IntelliJ, 
then navigate to the `dam/` package. To run the programs, go to each exercise's ``Main.kt`` 
and click the "Run" icon.

> **Diagram 2.** Directory diagram of kotlin project structure.
```
dam_tp2_kotlin/
└── src/
    └── main/
        └── kotlin/
            └── dam/
                └── (...)
```

3. **Execution (Android):** 

---

# Development Process
## 7. Version Control and Commit History
GitHub Desktop was used throughout the development process. Commits were made after the completion of each feature (e.g., "Add custom setter for Book class," "Implement calculator boolean logic"). This ensures the evolution of the code is tracked and can be reverted to stable versions if regressions occur.

> Note: Some commits only include progress checkpoints with unfinished code.
## 8. Difficulties and Lessons Learned
Reflecting on the development process, several technical challenges were encountered that deepened our understanding of Kotlin’s features and development best practices.


## 9. Future Improvements
A GUI could be implemented to manage the virtual library, together with data storage, either in XML or JSON files.

## 10. AI Usage Disclosure (Mandatory)
Artificial Intelligence was only used to draft this report, namely ``ChatGPT`` and ``Gemini``. All code in this project was developed manually, with assistance from official documentation.

---