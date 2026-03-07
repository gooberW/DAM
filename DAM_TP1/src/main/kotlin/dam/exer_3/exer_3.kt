package org.example.dam.exer_3

/*
A ball is dropped from a height of 100 meters. Each time it bounces, it reaches 60% of its
previous height. Write a program using generateSequence to model the bounce heights,
filter to keep only bounces that reach at least 1 meter high, take the first 15 qualifying
bounces, convert to a list, and print the heights rounded to 2 decimal places. Print the
sequence.
 */

fun main() {
    val initHeight = 100.0 // metros
    val bounce = 0.6
    // a cada bounce a altura é 60% da anterior
    println("Calculating bounces...")
    val bounces = generateSequence(initHeight * bounce) { it * bounce}.takeWhile{it>= 1.0}.toList()
    // usa-se o takeWhile porque nao sabemos quantos valores satisfazem a condição, assim impede-se que o
    // programa fique preso a procura de valores que nunca vao satisfazer.

    println("Bounce heights:")
    bounces.forEach {
        println("%.2f".format(it) + " m")
    }

}