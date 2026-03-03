package org.example.dam.exer_vl

class PhysicalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val weight: Int,
    val hasHardcover: Boolean = true
) : Book(title, author, publicationYear, availableCopies) {

    override fun getDetails():String {
        return super.getDetails() + ", Storage: Physical, Weight: ${weight}g, Has Hardcover?: ${hardcover}"
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