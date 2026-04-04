# Assignment 2 - Weather App
**Course:** LEIM

**Student:** A51692

**Date:** April 15th, 2026

**Repository URL:** https://github.com/gooberW/DAM

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