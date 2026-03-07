package tp1.dam.exer_vl

data class LibraryMember(
    val name: String,
    val membershipID: Int,
    val borrowedBooks: MutableList<String>
) {

    fun borrowBook(title: String) {
        borrowedBooks.add(title)
    }

    fun returnBook(title: String) {
        borrowedBooks.forEach{
            if(it == title) {
                borrowedBooks.remove(it)
            }
        }
    }
}