package tp1.dam.exer_vl

class Library(var name: String) {

    companion object Tracker {

        var numBooksAdded = 0

        fun getTotalBooksCreated():Int {
            return numBooksAdded
        }
    }

    var books = mutableListOf<Book>()

    fun addBook(book: Book) {
        books.add(book)

        Tracker.numBooksAdded++
    }

    fun borrowBook(title: String, member: LibraryMember) {
        //searches for book by title
        //decreases available copies by 1 f possible
        //prints success/failure message
        val book = books.find{it.title == title}

        if(book != null) {
            if(book.availableCopies > 0) {
                member.borrowBook(title)
                println("'${book.title}' (${book.publicationYear}) borrowed successfully by ${member}. Copies remaining: " +
                        "${book.availableCopies}")
                book.availableCopies -= 1
            }
        }else {
            println("[Error] Book not found!")
        }
    }

    fun returnBook(title:String, member: LibraryMember) {
        //searches for book by title
        //increases available copies by 1 f possible
        //prints success/failure message
        val book = books.find{it.title == title}

        if(book != null) {
            book.availableCopies += 1
            member.returnBook(title)
            println("'${book.title}' (${book.publicationYear}) returned successfully by ${member}.")
        }else {
            println("[Error] Book not found!")
        }
    }

    fun showBooks(){
        //print details of all books
        println("${name}")
        books.forEach{
            println(it)
        }
    }

    fun searchByAuthor(author: String) {
        println("Books by ${author}:")
        books.forEach{
            if(it.author.equals(author)) {
                var copy = "copies"
                if(it.availableCopies == 1) copy = "copy"
                println("- ${it.title} (${it.era}, ${it.availableCopies} ${copy} available)")
            }
        }
    }
}