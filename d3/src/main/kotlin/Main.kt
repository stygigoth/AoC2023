import java.io.File

fun main(args: Array<String>) {
    var input = File("input").readLines()
    input.forEach {
        println(it)
    }
}