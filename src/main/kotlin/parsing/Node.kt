package fr.spse.parsing

// FIXME some toString() representations aren't accurate anymore
abstract class Node(val children: List<Node>) {

    override fun toString(): String {
        return "${this.javaClass.simpleName}(children=${children})"
    }

    /**
     * @return The character representation of the node
     */
    open fun print(): String {
        return children.joinToString(separator = "") { it.print() }
    }
}

class BinaryExpressionNode(children: List<Node>): Node(children) {
    override fun print(): String {
        return "(${super.print()})"
    }
}

class ConstantExpressionNode(val negativeValue: Boolean, children: List<Node>): Node(children) {
    override fun print(): String {
        return "${if (negativeValue) "-" else ""}${super.print()}"
    }
}

class ExpressionNode(children: List<Node>): Node(children) {
    var value: String = ""
        private set

    constructor(value: String) : this(emptyList()) {
       this.value = value
    }

    override fun print() = "$value${super.print()}"
}

class NumberNode(children: List<Node>): Node(children)



/**
 * A node with a simple string representation
 */
open class TerminalNode(val value: String): Node(emptyList()) {
    override fun toString(): String {
        return "${this.javaClass.simpleName}(value=$value)"
    }

    override fun print() = value
}

class DigitNode(value: String): TerminalNode(value)
class OperationNode(value: String): TerminalNode(value)



