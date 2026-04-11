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
The project is divided into individual exercises, each designed to demonstrate specific Kotlin and Android development concepts.

**Exercise 1.1** focuses on sealed classes, extension functions, and higher-order functions. It demonstrates how Kotlin can be used to model restricted hierarchies of types and how functions can be passed and reused to create more flexible and reusable code.

**Exercise 1.2** implements a generic in-memory cache. The main goal is to understand generics and type safety in Kotlin. A challenge feature is the implementation of a filterValues function, which uses a predicate to return only values that match a given condition.

**Exercise 1.3** develops a data processing pipeline using Kotlin lambdas. This exercise demonstrates functional programming concepts such as chaining operations. The challenge part includes implementing compose and fork functions to combine and split processing flows.

**Exercise 1.4 **involves building a mathematical 2D vector library. This exercise focuses on operator overloading and Kotlin language features such as left-hand multiplication support and destructuring declarations for cleaner syntax and usability.

**Android - The Cool Weather App** is the final practical component of the project. It is an Android application developed in Android Studio that retrieves real-time weather data using the Open-Meteo API. The app processes weather information based on user location (latitude and longitude, or GPS in extended versions) and displays current conditions such as temperature, wind speed, humidity, and pressure. It also uses weather codes to dynamically select icons and background visuals, demonstrating API integration, asynchronous processing, and resource management in an Android environment.

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

> Listing 1. ``filterValues`` function, from scratch.
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

`buildPipeline` accepts a lambda with `Pipeline` as its receiver, shown in the Listing 2 below.

> Listing 2. 
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
reversing the vector's direction, seen on the Listing 3 below.

>Listing 3. Arithemtic operation examples.
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

> Listing 4. Normalization function.
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
with an error message, seen in the Listing 5 below.

> Listing 5. Implementation of the ``get`` operator.
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

The Cool Weather App is an Android application developed in Kotlin that retrieves and displays real-time weather data based on user-provided geographic coordinates (latitude and longitude). Weather data is obtained from the Open-Meteo API using an HTTP request built dynamically with the provided input values.

Network communication is handled on a background thread to avoid blocking the main UI thread. The response from the API is received in JSON format and parsed into Kotlin data classes using the Gson library. After parsing, the UI is updated using ``runOnUiThread`` to ensure thread safety.

The application displays key meteorological parameters including current temperature, wind speed, wind direction, humidity, and atmospheric pressure. The current weather conditions are identified using WMO weather codes returned by the API.

Instead of using hardcoded mappings, weather codes are linked to application resources using XML arrays. These arrays define mappings between weather codes, descriptions, icons, and background images, that can be seen in the listing 6 below. The application retrieves the correct index for a given weather code and uses it to access the corresponding resources. This approach improves maintainability by separating logic from static data.

> Listing 6. Snippet of resource file containing the icons, backgrounds and weather codes.
```
...

<array name="weather_icons">
    <item>@drawable/sunny_icon</item>
    <item>@drawable/sunny_icon</item>
    <item>@drawable/sunny_icon</item>

    ...

</array>
```

Weather icons are applied dynamically using ``ImageView.setImageResource``, while the background of the main layout is updated based on the weather condition category (e.g., clear vs overcast). A simplified visual system is used where only two main background states are supported.

Basic input validation is implemented to ensure that latitude and longitude values are numeric before making the API request. If invalid input is detected, the app displays a ``Toast`` message.

The application uses standard Android components such as ``AppCompatActivity``, ``ConstraintLayout``, and view binding through ``findViewById``. External dependencies include Gson for JSON deserialization and AndroidX libraries for core UI functionality.

## 5. Testing and Validation
**Testing Strategy:** Functional testing was performed by running the console applications in IntelliJ and deploying the Android apps to the Pixel 9 Pro AVD.

(...)

## 6. Usage Instructions
1. **Requirements:** Android Studio, IntelliJ IDEA, and Kotlin SDK.

2. **Execution (Kotlin):** Open the ``dam_tp2_kotlin`` project in IntelliJ, 
then navigate to the `dam/` package. To run the programs, go to each exercise's ``Main.kt`` 
and click the "Run" icon.

> Diagram 2. Directory diagram of kotlin project structure.
```
dam_tp2_kotlin/
└── src/
    └── main/
        └── kotlin/
            └── dam/
                └── (...)
```

3. **Execution (Android):** Go into Android Studio and run the application on the PIxel 9 Pro AVD. To see weather changes, write latitude and longitude values into the respective inputs and update.

---

# Development Process
## 7. Version Control and Commit History
GitHub Desktop was used throughout the development process. Commits were made after the completion of each feature (e.g., "Add custom setter for Book class," "Implement calculator boolean logic"). This ensures the evolution of the code is tracked and can be reverted to stable versions if regressions occur.

> Note: Some commits only include progress checkpoints with unfinished code.
## 8. Difficulties and Lessons Learned
During the development of the Cool Weather App, several technical challenges were encountered. One of the main difficulties was handling asynchronous network calls correctly. Since Android does not allow network operations on the main thread, background threading had to be implemented to fetch weather data without freezing the UI.

A further issue was the handling of weather codes and resource mapping. Early implementations used hardcoded values, which made the system difficult to maintain. This was improved by moving mappings into XML resource arrays, which allowed better separation of data and logic. However, aligning indexes across multiple arrays (codes, icons, descriptions, backgrounds) required careful consistency to avoid mismatches.

Additionally, selecting appropriate weather icons and backgrounds highlighted the importance of clean resource management. Initial implementations using string-based resource lookup caused inefficiencies and were replaced with more structured approaches.


## 9. Future Improvements
In future versions of the Cool Weather App, several enhancements could improve both functionality and user experience. A key improvement would be expanding the set of weather backgrounds and icons to more accurately represent different weather conditions instead of using only two main background states.

The app could also benefit from adding simple animations, such as transitions when weather conditions change or animated icons for dynamic weather effects like rain or clouds. This would make the interface more visually engaging.

Finally, refactoring the project into a more structured architecture (such as MVVM) would improve scalability and maintainability as the application grows.

## 10. AI Usage Disclosure (Mandatory)
Artificial Intelligence was only used to draft this report, namely ``Claude`` and ``ChatGPT``. All code in this project was developed manually, with assistance from official documentation.
