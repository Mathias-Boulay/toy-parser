package fr.spse

import fr.spse.exceptions.ParserError


class Parser(private var input: String) {

    /**
     * Consume the char from the input stream
     * @return The consumed char
     */
    private fun consume(expected: Char? = null): Char {
        val test = (25+1)
        if(expected != null && input[0] != expected)
            throw ParserError("Failed to consume, expected $expected, got $input")

        val consumedChar = input[0]
        input = input.substring(1, input.length)
        return consumedChar
    }

    /**
     * Parse and return a single digit
     */
    fun parseDigit(): DigitNode {
        if(input[0].isDigit()) {
            return DigitNode(consume().toString())
        }
        throw ParserError("Not a digit: $input")
    }

    /**
     * Parse and return a number
     */
    fun parseNumber(): NumberNode {
        val children = mutableListOf<Node>()
        // At least one digit has to be there
        children.add(parseDigit())

        // Check if there is a number ahead
        if (input[0].isDigit()) {
            children.add(parseNumber())
        }
        return NumberNode(children)
    }

    fun parseOperation(): OperationNode {
        if (charArrayOf('+', '-', '*').find { it == input[0] } != null) {
            return OperationNode(consume().toString())
        }
        throw ParserError("Not an operation: $input")
    }

    fun parseConstantExpression(): ConstantExpressionNode {
        val children = mutableListOf<Node>()
        // Optional negative number
        if (input[0] == '-') {
            children.add(TerminalNode(consume().toString()))
        }

        children.add(parseNumber())
        return ConstantExpressionNode(children)
    }

    fun parseBinaryExpression(): BinaryExpressionNode {
        val children = mutableListOf<Node>()

        children.add(TerminalNode(consume('(').toString()))
        children.add(parseExpression())
        children.add(parseOperation())
        children.add(parseExpression())
        children.add(TerminalNode(consume(')').toString()))

        return BinaryExpressionNode(children)
    }

    fun parseExpression(): ExpressionNode {
        // The grammar specifies "element" as one of the possibilities
        // I have a feeling it should have been <number> instead.
        // You get what you describe though
        if (input[0] == 'e') {
           // TODO consume all chars
        } else if (input[0] == '(') {
            return ExpressionNode(listOf(parseBinaryExpression()))
        } else {
            return ExpressionNode(listOf(parseConstantExpression()))
        }
        throw ParserError("Failed to parse expression: $input")
    }

}

