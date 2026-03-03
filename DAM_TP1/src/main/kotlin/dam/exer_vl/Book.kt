package org.example.dam.exer_vl

open class Book (
    val title : String,
    val author : String,
    val publicationYear : Int,
    val initialCopies: Int
) {
    fun getEra():String {
        when {
            publicationYear < 1980 -> return "Classic"
            publicationYear in 1980..2010 -> return "Modern"
            else -> return "Contemporary"
        }
    }

    var availableCopies: Int = initialCopies
        set(ammount) {
            if(ammount < 0) return

            field = ammount

            if(ammount == 0) {
                println("Warning: Book is now out of stock!")
            }
        }

    open fun getDetails(): String{
        return "Title: ${title}, Author: ${author}, Era: ${getEra()}, " +
                "Available: ${availableCopies} copies"
    }

    init{
        println("New Book Created!")
        println("Title: ${title}")
        println("Author: ${author}")
        println()
    }
}

