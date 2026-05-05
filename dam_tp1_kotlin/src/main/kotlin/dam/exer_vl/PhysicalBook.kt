package tp1.dam.exer_vl

class PhysicalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    private val weight: Int,
    private val hasHardcover: Boolean = true
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo():String {
        return "Storage: Physical, Weight: ${weight}, hasHardcover: $hardcover"
    }

    private val hardcover : String
        get() {
            return if(hasHardcover) {
                "Yes"
            }else {
                "No"
            }
        }

}