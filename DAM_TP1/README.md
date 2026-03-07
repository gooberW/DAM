# Assignment 1 - Hello Kotlin. Hello Android World!
<!-- Replace X and Title -->
Course: LEIM
Student: A51692
Date: March 8th, 2026
Repository URL: 

---

## 1. Introduction
This assignment provides an introduction to the Kotlin programming language and the Android development environment. The objective was to acquire a solid foundation in mobile development, covering everything from Kotlin syntax and object-oriented programming for building and debugging our first Android applications.

## 2. System Overview
The project is divided into individual exercises to demonstrate specific programming and development concepts:

- **Exercise 1:** Kotlin basic types and array initialization.

- **Exercise 2:** Console-based calculator with bitwise, boolean, and arithmetic operations.

- **Exercise 3:** Modeling physical sequences (ball bounce) using ``generateSequence``.

- **Android "Hello World" Apps:** Creation of an Android app using TextView, strings.xml, and logging.

- **Virtual Library:** An object-oriented system using inheritance, polymorphism, and abstraction to manage physical and digital books.

## 3. Architecture and Design
We adopted a modular package structure to keep exercises isolated and maintainable. This ensures that the namespace for each exercise is clean.
```
dam/
├── exer_1/
│   └── exer_1.kt
├── exer_2/
│   └── exer_2.kt
├── exer_3/
│   └── exer_3.kt
└── exer_vl/
    ├── Book.kt
    ├── Library.kt
    └── ... (other classes)
```

## 4. Implementation
### 4.1 Kotlin Exercises

**Exercise 1:** Implemented three different ways to initialize an `IntArray` with 50 perfect squares, using the ``IntArray`` constructor, range with `map()`, and Array with an initialization lambda.

**Exercise 2:** Built a calculator using a ``while`` loop for the main menu, a ``when`` expression for operation handling, and proper exception handling for division by zero.

**Exercise 3:** Used ``generateSequence`` to model the 60% height decay of a bouncing ball, using ``takeWhile`` where the sequence is only generated until the value is greater than $1m$.

``` 
val bounces = 
    generateSequence(initHeight * bounce) { it * bounce}.takeWhile{it>= 1.0}.toList()
```

> **Listing 1.** Usage of takeWhile.

### 4.2 Android Apps
**Hello World App:** Configured the basic Activity and Layout structures. We extracted strings to ``strings.xml`` for internationalization support and used custom attributes in the layout.

**System Info App:** Utilized ``android.os.Build`` to extract hardware information (Manufacturer, Model, SDK version) and display it in a TextView.

### 4.3 Virtual Library
**OOP:** Created an abstract base class ``Book`` with custom setters for ``availableCopies`` (including stock warnings) and custom getters for era calculation (Classic, Modern and Contemporary), seen in the listing below.

> **Listing 2.** Custom getter for book era.
``` 
val era:String
        get() {
            return if (publicationYear < 1980) "Classic"
            else if (publicationYear in 1980..2010) "Modern"
            else "Contemporary"
        }
```

**Inheritance:** Implemented ``DigitalBook`` (file size, format) and ``PhysicalBook`` (weight, hardcover status) as subclasses.

**Polymorphism:** Overrode ``getStorageInfo()`` and ``toString()`` to provide subclass-specific details without exposing the internal class type to the ``Library`` manager.

**Management:** The ``Library`` class uses a ``MutableList<Book>`` and a companion object to track the total number of books created across the system.

> **Listing 3.** Implementation of companion object.
```
companion object Tracker {

        var numBooksAdded = 0

        fun getTotalBooksCreated():Int {
            return numBooksAdded
        }
    }
```


**LibraryMember:** Implemented a data class to save the information for members, including all borrowed books.

>**Listing 4.** LibraryMember constructor.
```
data class LibraryMember(
    val name: String,
    val membershipID: Int,
    val borrowedBooks: MutableList<String>
) 
```
## 5. Testing and Validation
<!-- Testing strategy, test cases, scenarios, edge cases, and known limitations. -->
## 6. Usage Instructions
1. **Requirements:** Android Studio, IntelliJ IDEA, and Kotlin SDK.

2. **Execution (Kotlin):** Open the dam/ project in IntelliJ IDEA. Navigate to each exercise's main() function and click the "Run" icon.

3. **Execution (Android):** Open the HelloWorld or SystemInfo project in Android Studio. Launch the Pixel 9 Pro AVD from the Device Manager and deploy the application.
---
# Development Process
## 7. Version Control and Commit History
GitHub Desktop was used throughout the development process. Commits were made after the completion of each feature (e.g., "Add custom setter for Book class," "Implement calculator boolean logic"). This ensures that we can track the evolution of our code and revert to stable versions if regressions occur. 

> Some commits only include progress checkpoints with unfinished code.
## 8. Difficulties and Lessons Learned
Reflecting on the development process, several technical challenges were encountered that deepened our understanding of Kotlin’s features and development best practices.

**Managing Infinite Sequences (Exercise 3)**
One of the primary challenges involved the implementation of the bouncing ball exercise using ``generateSequence``. We initially overlooked the fact that sequences are lazy and potentially infinite. Our first approach using ``take(15)`` proved to be incorrect, if the ball's height dropped below the 1-meter threshold before the 15th bounce, the sequence would hang, waiting for values that did not exist. Transitioning to ``takeWhile`` allowed the sequence to dynamically terminate the moment the bounce height condition was no longer met. This taught us that condition-based termination is often more robust than arbitrary count-based termination when dealing with dynamic physical models.

**Mutability vs. Immutability (var vs val)**
Transitioning from Java, our initial instinct was to declare all class members as mutable (var). However, working with data class LibraryMember highlighted the benefits of immutability. By using val wherever possible, we reduced potential side effects and made our code more predictable. We learned that while val makes a reference immutable, combining it with a MutableList still allows us to update the collection’s contents, providing a better balance between data safety and necessary flexibility.

## 9. Future Improvements
A GUI could be implemented to manage the virtual library, together with data storage, either in XML or JSON files.

---
## 10. AI Usage Disclosure (Mandatory)
<!-- List all AI tools used (e.g., ChatGPT, Copilot, etc.), how they were used, and
confirmation that you remain responsible for all content. -->

Artificial Intelligence was only used to draft the report, namely `ChatGPT` and `Gemini`. Every piece of code in this project is human made, with help from documentation.

---