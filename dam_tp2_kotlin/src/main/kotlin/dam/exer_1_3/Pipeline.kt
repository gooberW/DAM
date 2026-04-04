package dam.exer_1_3

class Pipeline {

    // cada elemento da lista vai ser um par com o nome da stage e a sua função de transformação
    private val stages: MutableList<Pair<String, (List<String>) -> List<String>>> = mutableListOf()

    //appends a named stage to the pipeline
    fun addStage(name: String, transform: (List<String>) -> List<String> ) {
        stages.add(name to transform)
    }

    //runs the input through every stage in order and returns the final result.
    fun execute(input: List<String>): List<String> {
        var result = input
        for ((name, transform) in stages) {
            result = transform(result)
        }
        return result
    }

    // prints the name of each stage in order
    fun describe() {
        for ((index, stage) in stages.withIndex()) {
            println("${index + 1}. ${stage.first}")
        }

    }

    // CHALLENGE

    fun compose(firstName: String, secondName: String) {
        val first = stages.find { it.first == firstName }?.second
        if (first == null) error("Stage '$firstName' not found")

        val second = stages.find { it.first == secondName }?.second
        if (second == null) error("Stage '$secondName' not found")

        val composed: (List<String>) -> List<String> = { input -> second(first(input)) }

        stages.removeIf { it.first == firstName || it.first == secondName }
        stages.add("$firstName + $secondName" to composed)
    }

    fun fork(input: List<String>, other: Pipeline): Pair<List<String>, List<String>> {
        return Pair(this.execute(input), other.execute(input))
    }
}

/**
 * Accepts a lambda with Pipeline as its receiver.
 * Creates a Pipeline instance, applies the lambda to it, and returns it
 */
fun buildPipeline(block: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.block()
    return pipeline
}