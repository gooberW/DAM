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
      - [Sealed Classes](#sealed-classes)
      - [Higher-Order Functions](#higher-order-functions)
    - [4.2. In-Memory Cache](#42-in-memory-cache)
      - [Generics](#generics)
      - [Core Operations](#core-operations)
      - [Higher-Order Functions](#higher-order-functions-1)
      - [Challenge](#challenge)
    - [4.3. Configurable Data Pipeline](#43-configurable-data-pipeline)
      - [Design](#design)
      - [Higher-Order Functions](#higher-order-functions-2)
      - [Receiver Lambdas](#receiver-lambdas)
      - [Challenge](#challenge-1)
    - [4.4. 2D Vector Library](#44-2d-vector-library)
      - [Arithmetic Operators](#arithmetic-operators)
      - [Magnitude, Dot Product and Normalization](#magnitude-dot-product-and-normalization)
      - [Index Operator](#index-operator)
      - [Comparison Operator](#comparison-operator)
      - [Challenge](#challenge-2)
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
The project is divided into individual exercises, to demonstrate specific programming and development concepts:

- **Exercise 1.1:** Sealed classes, extension functions and high-order functions.

- **Exercise 1.2:** Generic in-memory cache.
  - **Challenge:**

- **Exercise 1.3:** Data processing pipeline in Kotlin using lambdas.
    - **Challenge:**
  
- **Exercise 1.4:** Mathematical 2D vector library.
    - **Challenge:** 
  
- **Android - The Cool Weather App:** 

## 3. Architecture and Design
A modular package structure was used to keep exercises isolated and maintainable. This ensures that the namespace for each exercise remains clean.

> Diagram 1. Directory diagram of Kotlin exercises project structure.
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

This exercise models a simple event log system using three core Kotlin concepts:
sealed classes, extension functions, and higher-order functions.

#### Sealed Classes

`Event` is declared as a sealed class [^1], meaning all its subclasses must be defined
within the same file. This gives the compiler complete knowledge of every possible
subtype, enforcing exhaustiveness in `when` expressions, where if a branch is missing,
the code will not compile. Each subclass (`Login`, `Purchase`, `Logout`) passes
its arguments up to the parent constructor and overrides `toString()` for
human-readable output.

[^1]: Kotlin Documentation, Sealed classes and interfaces (https://kotlinlang.org/docs/sealed-classes.html)

#### Higher-Order Functions

`processEvents` is a higher-order function [^2], which is a function that takes another function
as a parameter. The `handler: (Event) -> Unit` parameter is a lambda that the caller
defines, decoupling how each event is processed from the act of iterating over
them. This separation of concerns is a core principle of functional programming:
the function knows nothing about what the handler does, it simply applies it to
each event.

[^2]: Kotlin Documentation, Higher-order functions and lambdas (https://kotlinlang.org/docs/lambdas.html)

### 4.2. In-Memory Cache

This exercise implements a generic, in-memory key-value cache using Kotlin's
generics, higher-order functions, and the standard library's `MutableMap`.

#### Generics

`Cache<K : Any, V : Any>` is parameterized over two types: a key `K` and a
value `V`. The `: Any` constraint excludes nullable types, ensuring that neither
keys nor values can be `null`. This is important because the cache uses `null`
as a sentinel value [^3] to detect missing keys, allowing nullable keys or values
would make it impossible to distinguish between "key not found" and "key exists
with a null value".

[^3]: Kotlin Apprentice, Chapter 7: Nullability (https://www.kodeco.com/books/kotlin-apprentice/v2.0/chapters/7-nullability)

#### Core Operations

The cache wraps a `MutableMap<K, V>` internally, keeping it `private` to prevent
external mutation. The public API exposes only controlled operations:

- `put` stores a key-value pair
- `get` retrieves a value by key, returning `null` if absent
- `evict` removes an entry by key
- `size` returns the number of entries
- `snapshot` returns an immutable copy of the internal map via `toMap()`,
  protecting internal state from outside modification

#### Higher-Order Functions

`getOrPut` accepts a lambda `default: () -> V`, which is a function that computes a
value only if the key is absent. This is a common pattern in functional
programming where the default value is not computed unless
it is actually needed, avoiding unnecessary work.

`transform` accepts a lambda `action: (V) -> V` that receives the current value
and returns a new one. This allows the caller to define any transformation
without the cache needing to know what that transformation is. It returns a
`Boolean` indicating whether the key existed and the transformation was applied,
making it safe to call on keys that may not be present.

#### Challenge

`filterValues` accepts a predicate `(V) -> Boolean` and returns an immutable
`Map<K, V>` containing only the entries whose values satisfy it. Rather than
delegating to the standard library's built-in `filterValues`, the implementation
is written from scratch to make the logic explicit: iterate over every key-value
pair, apply the predicate to the value, and collect matching entries into a
temporary `MutableMap`. A final call to `toMap()` converts it to an immutable
copy before returning, consistent with the principle of exposing read-only
collections at the API boundary.

> Listing W. ``filterValues`` function, from scratch.
```
fun filterValues(predicate: (V) -> Boolean): Map<K, V> {
  //return cache.filterValues(predicate)

  val result = mutableMapOf<K, V>();
   for((key, value) in cache) {
    if(predicate(value)) {
      result[key] = value
    }
  }
  return result.toMap()
}
```


### 4.3. Configurable Data Pipeline

This exercise implements a composable, stage-based processing pipeline using
higher-order functions, receiver lambdas, and function composition.

#### Design

`Pipeline` internally stores an ordered list of named transformation stages, where
each stage is a `Pair<String, (List<String>) -> List<String>>`, that is a name paired
with a function that transforms a list of strings into another. Using a `Pair`
keeps the name and function together as a single unit, making it straightforward
to iterate, describe, and look up stages by name.

#### Higher-Order Functions

Each stage is itself a higher-order function, a `(List<String>) -> List<String>`
that the caller defines and the pipeline stores. This means the pipeline is
completely agnostic to what any stage does; it only knows that each stage takes
a list and returns a list. `addStage` simply appends the name and function as a
pair to the internal list.

The `execute` function runs the input through every stage in order, passing the output of each
stage as the input to the next. This pattern, where each function's output feeds
into the next, is known as a pipeline or function chaining, and is a
fundamental concept in functional programming.

#### Receiver Lambdas

`buildPipeline` accepts a lambda with `Pipeline` as its receiver, shown in the Listing X below.

```
fun buildPipeline(block: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.block()
    return pipeline
}
```

A receiver lambda gives the lambda body implicit access to the receiver's members,
so `addStage` can be called directly inside the block without any reference to the
pipeline instance.

#### Challenge

The `compose` function merges two existing stages into one by looking them up by name and
chaining them manually, where the output of the first becomes the input of the second.
Both original stages are then removed from the list and replaced with a single
combined stage. This is equivalent to what Kotlin's standard `andThen` [^5] does for
function composition, making two steps logically and structurally one.

The `fork` function runs the same input through two independent pipelines and returns both
results as a `Pair`. Since each pipeline maintains its own `stages` list, they
do not interfere with each other. This is useful when the same data needs to be
processed in two different ways simultaneously, for example, filtering errors
in one branch and filtering info logs in another.

[^5]: Medium, Concept of function composition in Kotlin (https://medium.com/@appdevinsights/concept-of-function-composition-in-kotlin-f3c3ea4d2649)

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

#### Challenge

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
Artificial Intelligence was only used to draft this report, namely ``Claude`` and ``ChatGPT``. All code in this project was developed manually, with assistance from official documentation.

---