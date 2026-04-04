package dam.exer_1_1

sealed class Event(val username: String, val timestamp: Long) {

    class Login(username: String, timestamp: Long) : Event(username, timestamp) {
        override fun toString() = "Login(username=${username}, timestamp=${timestamp})"
    }

    class Purchase(username: String, val amount: Double, timestamp: Long) : Event(username, timestamp) {
        override fun toString() = "Purchase(username=${username} , " +
                "amount=${amount}, " +
                "timestamp=${timestamp})"
    }

    class Logout(username: String, timestamp: Long) : Event(username, timestamp) {
        override fun toString() = "Logout(username=${username}, timestamp=${timestamp})"
    }
}

// higher-order function
fun processEvents(events: List<Event>, handler: (Event) -> Unit) {
    events.forEach { handler(it) }
}

fun List<Event>.filterByUser(username: String): List<Event> =
    filter { event ->
        when (event) {
            is Event.Login -> event.username == username
            is Event.Purchase -> event.username == username
            is Event.Logout -> event.username == username
        }
    }

fun List<Event>.totalSpent(username: String): Double =
    filterIsInstance<Event.Purchase>()
        .filter {it.username == username}
        .sumOf {it.amount}