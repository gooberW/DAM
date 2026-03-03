package org.example.dam.exer_vl

class DigitalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val fileSize: Double,
    val format: String
) : Book(title, author, publicationYear, availableCopies) {

    override fun getDetails():String {
       return super.getDetails() + ", Storage: Digital, File Size: ${fileSize} MB, Format: ${format}"
    }
}