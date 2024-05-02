package fr.spse.exceptions

/** Default error for the parser */
open class ParserException(message: String): Throwable(message)
class DigitParserException(message: String): ParserException(message)
class OperationParserException(message: String): ParserException(message)
class ConsumeParserException(message: String): ParserException(message)

