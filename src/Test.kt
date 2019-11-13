/**
 * Этот файл не относится к проекту "Множество Мандельброта".
 * Используется для демонстрации работы с лямбда-выражениями.
 */
class Arr(val n: Int, generator: (Int) -> Double) {
    val arr: Array<Double>

    init {
        arr = Array(n) { 0.0 }
        for (i in arr.indices) {
            arr[i] = generator(i)
        }
    }

    fun joinToString(): String {
        val s = StringBuilder()
        for (i in arr) {
            s.append(i)
            s.append(" ")
        }
        return s.toString()
    }
}

var f: ((Int) -> Double)? = null

fun main(args: Array<String>) {
    val a = Array(10, { it * it })
    println(a.joinToString())
    f = { 1.0 / it }
    val myArr = Arr(10, f ?: { 0.0 })
    println(myArr.joinToString())
}