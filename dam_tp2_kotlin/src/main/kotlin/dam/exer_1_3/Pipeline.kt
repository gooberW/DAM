package dam.exer_1_3

class Pipeline {

    //appends a named stage to the pipeline
    fun addStage(name: String, transform: (List<String>) -> List<String> ) {

    }

    //runs the input through every stage in order and returns the final result.
    fun execute(input: List<String>): List<String> {

    }

    // prints the name of each stage in order
    fun describe() {

    }
}

/**
 * Accepts a lambda with Pipeline as its receiver.
 * Creates a Pipeline instance, applies the lambda to it, and returns it
 */
fun buildPipeline() {

}