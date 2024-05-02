package fr.spse.exceptions

import fr.spse.Parser

/** Default error for the parser */
open class ParserError(message: String): Throwable(message)
class DigitParserError(message: String): ParserError(message)
class OperationParserError(message: String): ParserError(message)
class ConsumeParserError(message: String): ParserError(message)

