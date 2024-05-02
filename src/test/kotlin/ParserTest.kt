import fr.spse.parsing.Parser
import fr.spse.exceptions.ConsumeParserException
import fr.spse.exceptions.DigitParserException
import fr.spse.exceptions.ParserException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ParserTest {
    /** Basic parsing tests, they shouldn't throw any exception */

    @Test
    fun parseDigit() {
        Parser("5").parse()
    }

    @Test
    fun parseNegativeDigit() {
        Parser("-5").parse()
    }

    @Test
    fun parseNumber() {
        Parser("53").parse()
    }

    @Test
    fun parseNegativeNumber() {
        Parser("-53").parse()
    }

    @Test
    fun parseElement() {
        Parser("element").parse()
    }

    @Test
    fun parseBinaryExpression() {
        Parser("(5+3)").parse()
    }

    @Test
    fun parseComplexExpression() {
        Parser("((5+3)*-2)").parse()
    }

    /** Parsing tests that should throw an exception */

    @Test
    fun parseInvalidDigit() {
        assertThrows(DigitParserException::class.java) {
            Parser("a").parse()
        }
    }

    @Test
    fun parseInvalidNegativeNumber() {
        assertThrows(DigitParserException::class.java) {
            Parser("-a").parse()
        }
    }

    @Test
    fun parseInvalidNumber() {
        assertThrows(ConsumeParserException::class.java) {
            Parser("5a").parse()
        }
    }

    @Test
    fun parseInvalidElement() {
        assertThrows(ConsumeParserException::class.java) {
            Parser("element+5").parse()
        }
    }

    @Test
    fun parseInvalidBinaryExpression() {
        assertThrows(ConsumeParserException::class.java) {
            Parser("(5+3").parse()
        }
    }

    @Test
    fun parseInvalidComplexExpression() {
        assertThrows(ConsumeParserException::class.java) {
            Parser("((5+3)*-2").parse()
        }
    }

    @Test
    fun parseSpace() {
        assertThrows(ParserException::class.java) {
            Parser("(3+ 5)").parse()
        }
    }

    /** Printing tests */

    // small function to wrap the logic of the input and output being the same
    private fun assertPrint(input: String): Boolean {
        val parser = Parser(input)
        val node = parser.parse()
        return node.print() == input
    }

    @Test
    fun printDigit() {
        assertTrue(assertPrint("5"))
    }

    @Test
    fun printNegativeDigit() {
        assertTrue(assertPrint("-5"))
    }

    @Test
    fun printNumber() {
        assertTrue(assertPrint("53"))
    }

    @Test
    fun printNegativeNumber() {
        assertTrue(assertPrint("-53"))
    }

    @Test
    fun printElement() {
        assertTrue(assertPrint("element"))
    }

    @Test
    fun printBinaryExpression() {
        assertTrue(assertPrint("(5+3)"))
    }

    @Test
    fun printComplexExpression() {
        assertTrue(assertPrint("((5+3)*-2)"))
    }

    @Test
    fun printComplexExpressionWithElement() {
        assertTrue(assertPrint("((element+3)*-2)"))
    }
}