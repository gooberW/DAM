package org.example.dam.exer_2


fun main() {
    var mode = selectMode()
    while (mode != 0) {
        runCalc(mode)
        mode = selectMode()
    }
}


fun selectMode():Int {
    println("Select calculator mode:")
    println("1 - Arithmetic")
    println("2 - Boolean")
    println("3 - Bitwise Operations")
    println("0 - EXIT")

    var input = readln()
    var default = 0

    if (input == ""){
        println("Exiting the app...")
        return default
    } else {
        return input.toInt()
    }

}

fun runCalc(mode:Int) {
    var result: Any = ""

    when(mode) {
        1 -> result = showArithmeticOp()
        2 -> result = showBoolOp()
        3 -> result = showShiftOp()
    }

    println("Result: ${result}")
}

fun showArithmeticOp():Any {
    println("Type the first number:")
    val num1 = readln().toFloat();
    println("Type the second number:")
    val num2 = readln().toFloat();

    println("Select the intended operation typing the 3 letter code from the following list:")
    println("ADD - Addition")
    println("SUB - Subtraction")
    println("MUL - Multiplication")
    println("DIV - Division")

    return handleArithmetic(num1, num2, readln())
}

fun showBoolOp(): Boolean {
    println("Type the first value (true or false):")
    val val1 = readln().toBoolean();
    println("Type the second value (true or false):")
    val val2 = readln().toBoolean();

    println("Select the intended operation typing the 3 letter code from the following list:")
    println("AND - Boolean AND")
    println("OR - Boolean OR")
    println("NOT - Boolean NOT")

    return handleBoolean(val1, val2, readln())
}

fun showShiftOp():Any {
    println("Type the number:")
    val hexNum = readln().toInt();
    println("Type the shift amount:")
    val shift = readln().toInt();

    println("SHL - Bitwise Shift Left")
    println("SHR - Bitwise Shift Right")

    return handleShift(hexNum, shift, readln())
}

fun handleArithmetic(num1:Float, num2:Float, operation: String): Any {
    when(operation) {
        "ADD" -> return num1 + num2
        "SUB" -> return num1 - num2
        "MUL" -> return num1 * num2
        "DIV" -> if (num2 == 0f) return "ERR: Division by zero." else
                                    return num1 / num2
    }

    return "No Result"
}

fun handleBoolean(val1: Boolean, val2: Boolean, operation: String): Boolean {
    when(operation) {
        "AND" -> return val1 && val2
        "OR" -> return val1 || val2
    }

    return false
}

fun handleShift(hexNum: Int, shift: Int, operation: String):Any {
    val result: Int = when(operation) {
        "SHL" -> hexNum shl shift
        "SHR" -> hexNum shr shift
        else -> throw IllegalArgumentException("Operação Inválida")
    }

    return "HEX: 0x${Integer.toHexString(result).uppercase()}, INT: ${result}"
}