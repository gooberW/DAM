package tp1.dam.exer_vl

class DigitalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val fileSize: Double,
    val format: String
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo():String {
        return "Storage: Digital, File Size: ${fileSize}, Format: ${format}"
    }
}