package fr.spse

import fr.spse.parsing.Parser

fun main() {
    println("Hello World!")
    val parser = Parser("((element+15)*5)")
    val node = parser.parse()
    println(node)
    println(node.print())
}