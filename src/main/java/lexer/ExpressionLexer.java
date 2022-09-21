package lexer;
import java.io.Reader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * A class to represent a lexer that reads an expression to generate tokens with attributes.
 * The `peek` instance field stores the next character for the lexer to read.
 * A method can update `peek` to the next character by calling an overload of `readCharacter`.
 * A method that attempts to generate a token reads from `peek`, assuming that `peek` points to the start of that token.
 * The method reports invalid tokens using exceptions iff it has irreversibly read characters.
 */
public class ExpressionLexer implements Lexer<ExpressionTokenTag> {

    // reader for the lexer's input
    private final Reader reader;
    // the current character
    protected Character peek = null;
    // whether the previous token was a number token
    private boolean wasNumber = false;
    // `lineNumber` and `characterNumber` give the position of the lexer in the input.
    private int lineNumber = 1;
    private int characterNumber = 0;

    /**
     * Initialises this lexer to read a string.
     * @param inputString an input string
     * @throws IOException the reader throws an IO exception
     */
    public ExpressionLexer(String inputString) throws IOException {
        this(new StringReader(inputString));
    }

    /**
     * Initialises this lexer to read from a reader.
     * @param reader a reader
     * @throws IOException the reader throws an IO exception
     */
    public ExpressionLexer(Reader reader) throws IOException {
        this.reader = reader;
        // initialise `peek` to be the input's first character
        readCharacter();
    }

    /**
     * Updates `peek` to be the next unread character.
     * If the lexer reaches its input's end then it sets `peek` to `null`.
     * @throws IOException the reader throws an IO exception
     */
    protected void readCharacter() throws IOException {
        int nextInteger = reader.read();
        // `nextInteger` is `-1` iff the lexer has reached the end of the file.
        // Otherwise, it is a positive signed integer that the lexer can safely convert to an unsigned char.
        peek = nextInteger >= 0 ? (char)nextInteger : null;
        // increment the character number
        characterNumber++;
    }

    /**
     * Updates `peek` to be the first unread character that is not whitespace and determines whether that character
     * matches an expected character.
     * @param expected an expected character
     * @return whether the next character matches the expected character
     * @throws IOException the reader throws an IO exception
     */
    protected boolean readCharacter(Character expected) throws IOException {
        readCharacter();
        // determine whether the new current character that which we expect
        return peek == expected;
    }

    /**
     * Updates `peek` to be the first unread character that is not whitespace.
     * @throws IOException the reader throws an IO exception
     */
    private void skipWhitespace() throws IOException {
        // The lexer does nothing iff it has reached the end of the file or the current character is not whitespace.
        // Otherwise, it skips the current character.
        while (peek != null && Character.isWhitespace(peek)) {
            if (peek == '\n') {
                lineNumber++;
                characterNumber = 0;
            }
            readCharacter();
        }
    }

    /**
     * Attempts to parse a string representing a nonempty unsigned integer that has no leading zeros.
     * The string can be "0", representing `0`.
     * @return the value of a found unsigned integer and otherwise `null`
     * @throws IOException the reader throws an IO exception
     * @throws LeadingZeroException the integer has leading zeros
     */
    protected Integer getOptionalInteger() throws IOException, LeadingZeroException {
        // return `null` iff the input does not start with a digit
        if (peek == null || !Character.isDigit(peek)) {
            return null;
        }

        // initialise `value` to be the integer value of the input's first character
        int value = Character.digit(peek, 10);
        boolean isZero = value == 0;
        readCharacter();

        while (peek != null && Character.isDigit(peek)) {
            // throw a `LeadingZeroException` if characters appear after a first character of '0'
            if (isZero) {
                throw new LeadingZeroException(String.format(
                    "[ExpressionLexer:getInteger %d:%d] Illegal leading zero.",
                    lineNumber,
                    characterNumber
                ));
            }
            // recognise the current character as an additional digit of `value`
            value = 10 * value + Character.digit(peek, 10);
            // advance `peek`
            readCharacter();
        }
        return value;
    }

    /**
     * Attempts to parse a string representing a nonempty signed integer that has no leading zeros. Infers the sign.
     * @return the value of a found signed integer and otherwise `null`
     * @throws IOException the reader throws an IO exception
     * @throws LeadingZeroException the integer has leading zeros
     * @throws MissingIntegerException an integer does not follow a sign
     */
    protected Integer getOptionalSignedInteger() throws IOException, LeadingZeroException, MissingIntegerException {
        // initialise `sign` to be `peek` (and advance `peek`) iff `peek` is a sign
        Character sign = (peek != null && (peek == '+' || peek == '-')) ? peek : null;
        if (sign != null) {
            readCharacter();
        }
        // retrieve the integer that follows the optional sign
        Integer integer = getOptionalInteger();
        if (integer == null) {
            // throw a `MissingIntegerException` iff there is a sign with no following integer
            if (sign != null) {
                throw new MissingIntegerException(String.format(
                "[ExpressionLexer:getOptionalSignedInteger %d:%d] Sign without a following integer.",
                lineNumber,
                characterNumber
            ));
            }
            // return `null` iff there is neither a sign nor an integer
            return null;
        }

        // negate `integer` iff its sign indicates that it is negative
        if (sign != null && sign == '-') {
            integer *= -1;
        }
        return integer;
    }

    /**
     * Attempts to retrieve the fractional part of decimal number, starting directly after the decimal point.
     * @return the value of a found fractional part and otherwise `null`
     * @throws IOException the reader throws an IO exception
     */
    protected Double getOptionalFraction() throws IOException {
        // return `null` if the input does not start with a digit
        if (peek == null || !Character.isDigit(peek)) {
            return null;
        }

        // initialise `value` to be `0` (the default if nothing follows '.')
        double value = 0;
        int divisor = 10;
        while (peek != null && Character.isDigit(peek)) {
            // recognise the current character as an additional digit of `value`
            value = value + (double) Character.digit(peek, 10) / divisor;
            divisor *= 10;
            // advance `peek`
            readCharacter();
        }
        return value;
    }

    /**
     * Attempts to retrieve a number (with a value attribute) or sign (character token that represents a sign) token.
     * @return a found number token and otherwise `null`
     * @throws IOException the reader throws an IO exception
     * @throws EmptyNumberException the number's integral and fractional parts are empty, and it has a decimal point.
     * @throws MissingIntegerException an integer does not follow a sign or 'e'
     * @throws LeadingZeroException an integer has a leading zero
     */
    protected Token<ExpressionTokenTag> getOptionalNumberToken() throws
            IOException,
            EmptyNumberException,
            MissingIntegerException,
            LeadingZeroException
    {
        // initialise `sign` to be `peek` (and advance `peek`) iff `peek` is a sign
        Character sign = (peek != null && (peek == '+' || peek == '-')) ? peek : null;
        if (sign != null) {
            readCharacter();
        }

        // retrieve the number's integral part
        Integer integer = getOptionalInteger();

        // retrieve the number's fractional part iff it has a decimal point
        Double fraction = null;
        boolean fractional = peek != null && peek == '.';
        if (fractional) {
            // advance `peek` past '.'
            readCharacter();
            fraction = getOptionalFraction();
        }

        if (integer == null) {
            if (fractional && fraction == null) {
                // throw an `EmptyNumberException` iff the number is fractional and both its parts are empty
                throw new EmptyNumberException(String.format(
                    "[ExpressionLexer:getNumberToken %d:%d] Illegal empty number.",
                    lineNumber,
                    characterNumber
                ));
            } else if (!fractional) {
                // return a nonnull sign token iff the number is signed, empty and not fractional
                // Note that, in this case, `peek` has advanced exactly one space beyond the initial sign character.
                if (sign != null) {
                    return getOptionalCharacterToken(sign);
                }
                // return `null` iff the number is not signed, empty and not fractional
                return null;
            }
        }

        // retrieve the number's exponent iff the current input starts with 'e'
        Integer exponent = null;
        if (peek != null && peek == 'e') {
            // advance `peek` past 'e'
            readCharacter();
            exponent = getOptionalSignedInteger();
            // throw a `MissingIntegerException` if no integer follows 'e'
            if (exponent == null) {
                throw new MissingIntegerException(String.format(
                    "[ExpressionLexer:getNumberToken %d:%d] Missing exponent after 'e'.",
                    lineNumber,
                    characterNumber
                ));
            }
        }

        // initialise `value` to be `integer` if `integer` is nonnull, otherwise `0`
        float value = (integer == null) ? 0 : integer;
        // add a nonnull fractional part `value`
        if (fraction != null) {
            value += fraction;
        }
        // multiply `value` by `10` raised to a nonnull exponent
        if (exponent != null) {
            value *= Math.pow(10, exponent);
        }
        // negate `value` iff its sign indicates that it is negative
        if (sign != null && sign == '-') {
            value *= -1;
        }
        // wrap `value` in a number token
        return new NumberToken(value);
    }

    /**
     * Attempts to retrieve a character token. A token is a character token iff its lexeme is exactly one character.
     * @return a found token and otherwise `null`
     * @throws IOException the reader throws an IO exception
     */
    protected TagToken getOptionalCharacterToken() throws IOException {
        // map `peek` to a token and otherwise `null`
        TagToken tagToken = getOptionalCharacterToken(peek);
        // advance `peek` iff it produced a `token`
        if (tagToken != null) {
            readCharacter();
        }
        return tagToken;
    }

    /**
     * Attempts to retrieve a character token from an explicit character.
     * @return a found token and otherwise `null`
     */
    static protected TagToken getOptionalCharacterToken(Character character) {
        // map `character` to a token and otherwise `null`
        return switch (character) {
            case '+' -> new TagToken(ExpressionTokenTag.PLUS);
            case '-' -> new TagToken(ExpressionTokenTag.MINUS);
            case '^' -> new TagToken(ExpressionTokenTag.POWER);
            case '!' -> new TagToken(ExpressionTokenTag.FACTORIAL);
            default -> null;
        };
    }

    /**
     * Attempts to retrieve a cosine token.
     * @return a found cosine token and otherwise `null`
     * @throws IOException the reader throws an IO exception
     * @throws IncompleteCosineException the input starts with 'c' but does not continue to produce "cos"
     */
    protected TagToken getOptionalCosineToken() throws IOException, IncompleteCosineException {
        // return `null` iff the input does not start with 'c'
        if (peek != 'c') {
            return null;
        }
        // throw an `IncompleteCosineException` iff 'c' is not a prefix to "cos"
        if (!(readCharacter('o') && readCharacter('s'))) {
            throw new IncompleteCosineException(String.format(
                "[ExpressionLexer:getOptionalCosineToken %d:%d] 'c' should be a prefix to \"cos\".",
                lineNumber,
                characterNumber
            ));
        }
        // advance peek
        readCharacter();
        return new TagToken(ExpressionTokenTag.COSINE);
    }

    /**
     * Generates a token from the current input.
     * Returns `null` if the lexer reaches its input's end.
     * @return a found token
     * @throws IOException the reader throws an IO exception
     * @throws InvalidTokenException the current input produces an invalid token
     */
    public Token<ExpressionTokenTag> scan() throws IOException, InvalidTokenException {
        // skip all whitespace
        skipWhitespace();

        // return `null` iff the reader reaches its input's end
        if (peek == null) {
            return null;
        }

        // The lexer checks for a character then a number token iff `wasNumber` is set. It does the reverse otherwise.
        Token<ExpressionTokenTag> token;
        if (wasNumber) {
            token = getOptionalCharacterToken();
            if (token == null) { token = getOptionalNumberToken(); }
        } else {
            token = getOptionalNumberToken();
            if (token == null) { token = getOptionalCharacterToken(); }
        }
        if (token == null) { token = getOptionalCosineToken(); }
        // We throw an `IllegalCharacterException` if the current character is a prefix to no lexemes.
        if (token == null) {
            throw new IllegalCharacterException(String.format(
                "[ExpressionLexer:scan %d:%d] Illegal character",
                lineNumber,
                characterNumber
        )); }

        // set `wasNumber` iff the token is a number token
        wasNumber = token.getTag().equals(ExpressionTokenTag.NUMBER);
        return token;
    }

    /**
     * Generates all the tokens from the current input.
     * @return the resulting array of tokens
     * @throws IOException the reader throws an IO exception
     * @throws InvalidTokenException the current input produces an invalid token
     */
    public Token<ExpressionTokenTag>[] completeScan() throws IOException, InvalidTokenException {
        // initialise the list of tokens to be empty
        ArrayList<Token<ExpressionTokenTag>> tokens = new ArrayList<>();
        // push all nonnull tokens to `tokens`
        Token<ExpressionTokenTag> currentToken = scan();
        while (currentToken != null) {
            tokens.add(currentToken);
            currentToken = scan();
        }
        return tokens.toArray(new Token[0]);
    }
}
