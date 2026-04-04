package dam.exer_1_3

fun main() {
    val logs = listOf(
        " INFO : server started ",
        " ERROR : disk full ",
        " DEBUG : checking config ",
        " ERROR : out of memory ",
        " INFO : request received ",
        " ERROR : connection timeout "
    )

    //compose
    val pipeline = buildPipeline {
        addStage("Trim")          { list -> list.map { it.trim() } }
        addStage("Filter Errors") { list -> list.filter { it.startsWith("ERROR") } }
        addStage("Uppercase")     { list -> list.map { it.uppercase() } }
    }

    println("Before compose:")
    pipeline.describe()

    pipeline.compose("Trim", "Filter Errors")

    println("After compose:")
    pipeline.describe()

    println()
    println("Result:")
    pipeline.execute(logs).forEach { println(it) }

    //fork
    val errorPipeline = buildPipeline {
        addStage("Trim")          { list -> list.map { it.trim() } }
        addStage("Filter Errors") { list -> list.filter { it.startsWith("ERROR") } }
    }

    val infoPipeline = buildPipeline {
        addStage("Trim")         { list -> list.map { it.trim() } }
        addStage("Filter Info")  { list -> list.filter { it.startsWith("INFO") } }
    }

    val (errors, infos) = errorPipeline.fork(logs, infoPipeline)

    println()
    println("Errors:")
    errors.forEach { println(it) }

    println()
    println("Infos:")
    infos.forEach { println(it) }
}