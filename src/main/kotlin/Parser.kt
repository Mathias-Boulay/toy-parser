package fr.spse

import fr.spse.exceptions.ConsumeParserError
import fr.spse.exceptions.DigitParserError
import fr.spse.exceptions.OperationParserError
import fr.spse.exceptions.ParserError


/**
 * Parser who parses once, and only once.
 */
class Parser(private var input: String) {

    /**
     * @return The parsed input as an intermediate representation
     */
    fun parse(): ExpressionNode {
        val expression = parseExpression()
        expectConsumedInput()
        return expression
    }

    /**
     * Consume the char from the input stream
     * @return The consumed char
     */
    private fun consume(expected: Char? = null): Char {
        if(input.isEmpty()) throw ConsumeParserError("No more characters to consume.")

        if(expected != null && input[0] != expected)
            throw ConsumeParserError("Failed to consume $input, expected $expected, got ${input[0]}.")

        val consumedChar = input[0]
        input = input.substring(1, input.length)
        return consumedChar
    }

    private fun expectConsumedInput() {
        if (input.isNotEmpty()) throw ConsumeParserError("More characters than expected: $input")
    }

    /**
     * Parse and return a single digit
     */
    private fun parseDigit(): DigitNode {
        if(input[0].isDigit()) {
            return DigitNode(consume().toString())
        }
        throw DigitParserError("Not a digit: ${input[0]}")
    }

    /**
     * Parse and return a number
     */
    private fun parseNumber(): NumberNode {
        val children = mutableListOf<Node>()
        // At least one digit has to be there
        children.add(parseDigit())

        // Check if there is a number ahead
        if (input.isNotEmpty() && input[0].isDigit()) {
            children.add(parseNumber())
        }
        return NumberNode(children)
    }

    private fun parseOperation(): OperationNode {
        if (charArrayOf('+', '-', '*').find { it == input[0] } != null) {
            return OperationNode(consume().toString())
        }
        throw OperationParserError("Not an operation: ${input[0]}")
    }

    private fun parseConstantExpression(): ConstantExpressionNode {

        // Optional negative number
        var negativeSign = false
        if (input[0] == '-') {
            consume('-')
            negativeSign = true
        }

        return ConstantExpressionNode(negativeSign, listOf(parseNumber()))
    }

    private fun parseBinaryExpression(): BinaryExpressionNode {
        val children = mutableListOf<Node>()

        consume('(')
        children.add(parseExpression())
        children.add(parseOperation())
        children.add(parseExpression())
        consume(')')

        return BinaryExpressionNode(children)
    }

    private fun parseExpression(): ExpressionNode {
        // The grammar specifies "element" as one of the possibilities
        // I have a feeling it should have been <number> instead.
        // You get what you describe though
        if (input[0] == 'e') {
           for (char in "element"){
               consume(char)
           }
            return ExpressionNode("element")
        } else if (input[0] == '(') {
            return ExpressionNode(listOf(parseBinaryExpression()))
        } else {
            return ExpressionNode(listOf(parseConstantExpression()))
        }
    }

}

