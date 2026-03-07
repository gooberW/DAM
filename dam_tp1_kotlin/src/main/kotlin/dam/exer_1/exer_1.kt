package tp1.dam.exer_1

import kotlin.collections.MutableList as MutableList

fun main() {
    val size = 51

    // a)
    var perfectSquaresA = IntArray(size){it*it}
    perfectSquaresA = perfectSquaresA.sliceArray(1..size-1) // corta o 0
    println("a) Perfect squares (first 50): ${perfectSquaresA.contentToString()}")

    //b)
    val numbersB: MutableList<Int> = mutableListOf()

    for (i in 1..50) {
        numbersB.add(i)
    }

    val perfectSquaresB = numbersB.map {it*it}

    println("b) Perfect squares (first 50): ${perfectSquaresB}")

    //c)
    var perfectSquaresC = Array<Int>(size) {it*it}
    perfectSquaresC = perfectSquaresC.sliceArray(1..size-1) // corta o 0
    println("c) Perfect squares (first 50): ${perfectSquaresC.contentToString()}")
}