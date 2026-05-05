package tp1.dam.exer_vl

class DigitalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    private val fileSize: Double,
    private val format: String
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo():String {
        return "Storage: Digital, File Size: ${fileSize}, Format: $format"
    }
}