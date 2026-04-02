package main.kotlin.dam.exer_1_1

fun main() {
    val events = listOf (
        Event.Login ("alice", 1_000 ),
        Event.Purchase ("alice", 49.99, 1_100 ),
        Event.Purchase ("bob", 19.99, 1_200 ),
        Event.Login ("bob", 1_050 ),
        Event.Purchase ("alice", 15.00, 1_300 ),
        Event.Logout ("alice", 1_400 ),
        Event.Logout ("bob", 1_500 )
    )

    processEvents(events) { event ->
        val description = when (event) {
            is Event.Login    -> "[LOGIN] ${event.username} logged in at t=${event.timestamp}"
            is Event.Purchase -> "[PURCHASE] ${event.username} spent ${event.amount} at t=${event.timestamp}"
            is Event.Logout   -> "[LOGOUT] ${event.username} logged in at t=${event.timestamp}"
        }
        println(description)
    }

    println()

    println("Total spent by alice: \$${"%.2f".format(events.totalSpent("alice"))}")
    println("Total spent by bob: \$${"%.2f".format(events.totalSpent("bob"))}")

    println()

    val aliceEvents = events.filterByUser("alice");
    println("Events for alice:")
    for(event in aliceEvents) {
        println("   " + event)
    }
}

