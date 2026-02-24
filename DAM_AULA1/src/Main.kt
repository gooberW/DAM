import kotlin.collections.List as List
import kotlin.collections.mutableListOf as mutableListOf

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name = "Kotlin" // type is inferred
    // val name: String = "Kotlin" // type is specified
    // val -> immutable (const, final, read only)
    // var -> variable
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println("Hello, " + name + "!")

    val finalList: List<Int> = listOf(3, 2, 1) // read only list
    var mutableList: MutableList<Int> = mutableListOf() // list that can be operated on

    for (i in 1..5) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
        mutableList.add(i)
    }

    println("List after opperations: ${mutableList}")


    val num1 = 23
    val num2 = 25
    var max = checkMax(num1, num2)
    println("Max number between 23 and 25: ${max}")

    println("Select an opperation: (1-A, 2-B, 3-C; 0-QUIT)")
    var input = readln().toInt()
    while (input != 0) {
        selectOP(input)
        input = readln().toInt()
    }


}

fun checkMax(a: Int, b: Int): Int { // after ':' defines return type
    if(a > b) return a else return b
}

fun selectOP(opp: Int) {
    when (opp){ // 'when' replaces 'switch'
        1 -> println("A")
        2 -> println("B")
        3 -> println("C")
        else -> println("Unsupported operation")
    }

}
