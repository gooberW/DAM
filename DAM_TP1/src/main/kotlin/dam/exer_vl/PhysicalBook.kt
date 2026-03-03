package org.example.dam.exer_vl

class PhysicalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val weight: Int,
    val hasHardcover: Boolean = true
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo():String {
        return "Storage: Physical, Weight: ${weight}, hasHardcover: ${hardcover}"
    }

    val hardcover : String
        get() {
            if(hasHardcover) {
                return "Yes"
            }else {
                return "No"
            }
        }

}