package org.example.dam.exer_vl

abstract class Book (
    val title : String,
    val author : String,
    val publicationYear : Int,
    val initialCopies: Int
) {
    val era:String
        get() {
            return if (publicationYear < 1980) "Classic"
            else if (publicationYear in 1980..2010) "Modern"
            else "Contemporary"
        }

    var availableCopies: Int = initialCopies
        set(ammount) {
            if(ammount < 0) return

            field = ammount

            if(ammount == 0) {
                println("[Warning] '${title}' is now out of stock!")
            }
        }

    abstract fun getStorageInfo():String

    override fun toString(): String {
        return "Title: ${title}, Author: ${author}, Era: ${era}, Available Copies: ${availableCopies}, " +
                getStorageInfo()
    }

    init{
        println("New Book Created!")
        println("Title: ${title}")
        println("Author: ${author}")
        println()
    }
}

