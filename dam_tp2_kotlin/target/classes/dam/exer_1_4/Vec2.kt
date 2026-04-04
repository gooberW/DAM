package dam.exer_1_4

data class Vec2(var x: Double, var y: Double): Comparable<Vec2> {

    operator fun plus(increment: Vec2): Vec2 {
        return Vec2(x + increment.x, y + increment.y)
    }

    operator fun minus(decrement: Vec2): Vec2 {
        return Vec2(x - decrement.x, y - decrement.y)
    }

    operator fun times(multiplier: Double): Vec2 {
        return Vec2(x * multiplier, y * multiplier)
    }

    operator fun unaryMinus(): Vec2 {
        return Vec2(-x, -y);
    }

    override operator fun compareTo(other: Vec2): Int {
        return this.magnitude().compareTo(other.magnitude())
    }

    fun magnitude(): Double {
        return Math.sqrt(x*x + y*y)
    }

    fun dot(other: Vec2): Double {
        return (x*other.x + y*other.y)
    }

    fun normalized(): Vec2 {
        if (magnitude() == 0.0) {
            throw IllegalStateException("Cannot normalize a zero vector")
        }
        return Vec2(x / magnitude(), y / magnitude())
    }

    operator fun get(index: Int): Double = when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Index $index out of bounds, Vec2 only has indices 0 and 1")
    }
}