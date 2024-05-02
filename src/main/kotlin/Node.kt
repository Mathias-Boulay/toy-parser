package fr.spse

import java.util.*

// TODO create specialised node types
abstract class Node(val children: List<Node>) {

    override fun toString(): String {
        return "${this.javaClass.simpleName}(children=${children})"
    }
}

class NumberNode(children: List<Node>): Node(children)
class ConstantExpressionNode(children: List<Node>): Node(children)
class BinaryExpressionNode(children: List<Node>): Node(children)
class ExpressionNode(children: List<Node>): Node(children)



/**
 * A node with a simple string representation
 */
open class TerminalNode(val value: String): Node(emptyList()) {
    override fun toString(): String {
        return "${this.javaClass.simpleName}(value=$value)"
    }
}

class DigitNode(value: String): TerminalNode(value)
class OperationNode(value: String): TerminalNode(value)



