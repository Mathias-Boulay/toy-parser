package fr.spse

fun main() {
    println("Hello World!")
    val parser = Parser("((element+15)*5)")
    val node = parser.parse()
    println(node)
    println(node.print())
}