# Assignment 2 - Weather App
**Course:** LEIM

**Student:** A51692

**Date:** March 8th, 2026

**Repository URL:** https://github.com/gooberW/DAM

---

## 1. Introduction
This assignment provides an introduction to the Kotlin programming language and the Android development environment. The objective was to acquire a solid foundation in mobile development, covering everything from Kotlin syntax and object-oriented programming for building and debugging our first Android applications.

## 2. System Overview
The project is divided into individual exercises, to demonstrate specific programming and development concepts:

- **Exercise 1:** Sealed classes, extension functions and high-order functions.

- **Exercise 2:** Generic in-memory cache.
  - **Challenge:**

- **Exercise 3:** Data processing pipeline in Kotlin.
    - **Challenge:**
  
- **Exercise 4:** Mathematical 2D vector.
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
│   └── 
└── exer_1_4/
    ├── 
    ├── 
    └── 
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
**Hello World App:** The basic Activity and Layout structures were configured. Strings were extracted to ``strings.xml`` for internationalization support, and custom attributes were applied in the layout.

**System Info App:** ``android.os.Build`` was used to extract hardware information (Manufacturer, Model, SDK version) to be displayed in a TextView.

### 4.3 Virtual Library
**OOP:** An abstract base class ``Book`` was created with custom setters for ``availableCopies`` and custom getters for era calculation (Classic, Modern, and Contemporary).

> **Listing 2.** Custom getter for book era.
``` 
val era:String
        get() {
            return if (publicationYear < 1980) "Classic"
            else if (publicationYear in 1980..2010) "Modern"
            else "Contemporary"
        }
```

**Inheritance:** ``DigitalBook`` (file size, format) and ``PhysicalBook`` (weight, hardcover) were implemented as subclasses of `Book`.

**Polymorphism:** ``getStorageInfo()`` and ``toString()`` were overridden to provide subclass-specific details without exposing the internal class type to the ``Library`` manager.

**Management:** The ``Library`` class uses a ``MutableList<Book>`` and a companion object to track the total number of books added across all libraries.

> **Listing 3.** Implementation of companion object.
```
companion object Tracker {

        var numBooksAdded = 0

        fun getTotalBooksCreated():Int {
            return numBooksAdded
        }
    }
```


**LibraryMember:** A data class was implemented to store member information, including all borrowed books.

>**Listing 4.** LibraryMember constructor.
```
data class LibraryMember(
    val name: String,
    val membershipID: Int,
    val borrowedBooks: MutableList<String>
) 
```
## 5. Testing and Validation
**Testing Strategy:** Functional testing was performed by running the console applications in IntelliJ and deploying the Android apps to the Pixel 9 Pro AVD.

**Edge Cases:** Validation against non-numeric input and division by zero was implemented for the Calculator.

**Virtual Library:** Borrowing logic was validated against out-of-stock scenarios, and negative copy counts were prevented via setters.

**Logcat:** The Logcat monitor in Android Studio was used to verify lifecycle messages (onCreate) and debug variable states.

## 6. Usage Instructions
1. **Requirements:** Android Studio, IntelliJ IDEA, and Kotlin SDK.

2. **Execution (Kotlin):** Open the ``dam_tp1_kotlin`` project in IntelliJ, then navigate to the `dam/` package. To run the programs, go to each exercise's ``main()`` function and click the "Run" icon.

> **Diagram 2.** Directory diagram of kotlin project structure.
```
dam_tp1_kotlin/
└── src/
    └── main/
        └── kotlin/
            └── dam/
                └── (...)
```

3. **Execution (Android):** Open the HelloWorld or SystemInfo project in Android Studio. Launch the Pixel 9 Pro AVD from the Device Manager and deploy the application.
---
# Development Process
## 7. Version Control and Commit History
GitHub Desktop was used throughout the development process. Commits were made after the completion of each feature (e.g., "Add custom setter for Book class," "Implement calculator boolean logic"). This ensures the evolution of the code is tracked and can be reverted to stable versions if regressions occur.

> Note: Some commits only include progress checkpoints with unfinished code.
## 8. Difficulties and Lessons Learned
Reflecting on the development process, several technical challenges were encountered that deepened our understanding of Kotlin’s features and development best practices.

**Managing Infinite Sequences (Exercise 3)**
One of the primary challenges involved the implementation of the bouncing ball exercise using ``generateSequence``. We initially overlooked the fact that sequences are lazy and potentially infinite. Our first approach using ``take(15)`` proved to be incorrect, if the ball's height dropped below the 1-meter threshold before the 15th bounce, the sequence would hang, waiting for values that did not exist. Transitioning to ``takeWhile`` allowed the sequence to dynamically terminate the moment the bounce height condition was no longer met. This taught us that condition-based termination is often more robust than arbitrary count-based termination when dealing with dynamic physical models.

**Mutability vs. Immutability (var vs val)**
Transitioning from Java, the initial instinct was to declare all class members as mutable (``var``). However, working with data class LibraryMember highlighted the benefits of immutability. By using ``val`` wherever possible, potential side effects can be reduced and the code can be made more predicatble. While ``val`` makes a reference immutable, combining it with a ``MutableList`` still allows updates to the collection’s contents, providing a better balance between data safety and necessary flexibility.

## 9. Future Improvements
A GUI could be implemented to manage the virtual library, together with data storage, either in XML or JSON files.

## 10. AI Usage Disclosure (Mandatory)
Artificial Intelligence was only used to draft this report, namely ``ChatGPT`` and ``Gemini``. All code in this project was developed manually, with assistance from official documentation.

---