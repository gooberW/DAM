package org.example.dam.exer_vl

data class LibraryMember(
    val name: String,
    val membershipId: Int,
    val borrowedBooks: MutableList<String>
) {
}