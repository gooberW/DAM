package dam.exer_1_2

fun main() {
    var wordFreq = Cache<String, Int>()
    wordFreq.put("kotlin", 1)
    wordFreq.put("scala", 1)
    wordFreq.put("haskell", 1)

    println("--- Word Frequency Cache ---")
    println("Size: ${wordFreq.size()}")
    println("Frequency of 'kotlin': ${wordFreq.get("kotlin")}")
    println("Get or Put? 'kotlin': ${wordFreq.getOrPut("kotlin") { 1 }}")
    println("Get or Put? 'java': ${wordFreq.getOrPut("java") { 0 }}")
    println("Size after getOrPut: ${wordFreq.size()}")
    println("Transform 'kotlin' (+1): ${wordFreq.transform("kotlin") { it + 1 }}")
    println("Transform 'cobol' (+1): ${wordFreq.transform("cobol") { it + 1 }}")
    println("Snapshot: ${wordFreq.snapshot()}")
    println("CHALLENGE - Words with freq > 0: ${wordFreq.filterValues() {it > 0}}")

    var idReg = Cache<Int, String>()
    idReg.put(1, "Alice")
    idReg.put(2, "Bob")

    println("--- ID Registry Cache ---")
    println("ID 1 -> ${idReg.get(1)}")
    println("ID 2 -> ${idReg.get(2)}")
    idReg.evict(1)
    println("After evict on ID: 1, size is ${idReg.size()}")
    println("ID: 1 after eviction: ${idReg.get(1)}")
}