package fr.spse

fun main() {
    println("Hello World!")
    val parser = Parser("(53+15)")
    val node = parser.parseExpression()
    println(node)
}