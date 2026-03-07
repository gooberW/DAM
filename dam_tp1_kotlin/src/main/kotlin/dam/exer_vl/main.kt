package tp1.dam.exer_vl

fun main() {
    val centralLib = Library("Central Library")
    val westernLib = Library("Western Library")

    val member1 = LibraryMember("Gabriel", 1, mutableListOf())

    val digitalBook = DigitalBook(
        "Kotlin in Action",
        "Dmitry Jemerov",
        2017,
        5,
        4.5,
        "PDF"
    )
    val physicalBook = PhysicalBook(
        "Clean Code",
        "Robert C. Martin",
        2008,
        3,
        650,
        true
    )
    val classicBook = PhysicalBook(
        "1984",
        "George Orwell",
        1949,
        2,
        400,
        false
    )

    centralLib.addBook(digitalBook)
    centralLib.addBook(physicalBook)
    centralLib.addBook(classicBook)

    westernLib.addBook(digitalBook)
    westernLib.addBook(classicBook)

    println("--- Library Catalog ---")
    centralLib.showBooks()
    println()
    westernLib.showBooks()

    println("\n--- Borrowing Books ---")
    centralLib.borrowBook("Clean Code", member1)
    centralLib.borrowBook("1984", member1)
    centralLib.borrowBook("1984", member1)
    centralLib.borrowBook("1984", member1) // Should fail - no copies left
    println("\n--- Returning Books ---")
    centralLib.returnBook("1984", member1)
    println("\n--- Search by Author ---")
    centralLib.searchByAuthor("George Orwell")

    println("\nTotal books added to all libraries: ${Library.getTotalBooksCreated()}")
}
