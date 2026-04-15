/**
 * Parser for the Speek language.
 * Converts a list of tokens into executable instructions (AST level).
 */
package parser;

import execution.*;
import java.util.*;
import tokenizer.*;

public class Parser {

    private final List<Token> tokens;  // list of tokens from tokenizer
    private int pos = 0;              // current position in token list

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Returns current token without consuming it
    private Token current() {
        return tokens.get(pos);
    }

    // Consumes current token and moves to next
    private Token consume() {
        return tokens.get(pos++);
    }

    // Matches expected token type and consumes it if matched
    private boolean match(TokenType type) {
        if (current().getType() == type) {
            pos++;
            return true;
        }
        return false;
    }

    // Main parsing loop: converts tokens → list of instructions
    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();

        while (current().getType() != TokenType.EOF) {

            // Decide which instruction to parse based on keyword
            if (current().getType() == TokenType.LET) {
                instructions.add(parseAssign());
            }

            else if (current().getType() == TokenType.SAY) {
                instructions.add(parsePrint());
            }

            else if (current().getType() == TokenType.IF) {
                instructions.add(parseIf());
            }

            else if (current().getType() == TokenType.REPEAT) {
                instructions.add(parseRepeat());
            }

            // Skip empty lines
            else if (current().getType() == TokenType.NEWLINE) {
                consume();
            }

            // Unknown structure → syntax error
            else {
                throw new RuntimeException("Unexpected token: " + current().getValue());
            }
        }

        return instructions;
    }

    //  ASSIGN: let <varName> be <expression>
    private Instruction parseAssign() {
        consume(); // consume LET

        String varName = consume().getValue(); // variable name

        // Ensure 'be' keyword exists
        if (!match(TokenType.BE)) {
            throw new RuntimeException("Expected 'be'");
        }

        Expression expr = parseExpression(); // parse right-hand side

        return new AssignInstruction(varName, expr);
    }

    // 🔹 PRINT: say <expression>
    private Instruction parsePrint() {
        consume(); // consume SAY

        Expression expr = parseExpression(); // expression to print

        return new PrintInstruction(expr);
    }

    //  IF: if <expr> > <expr> then <single-line body>
    private Instruction parseIf() {
        consume(); // consume IF

        Expression left = parseExpression();

        // Expect 'is greater than' → already tokenized as GREATER_THAN
        if (!match(TokenType.GREATER_THAN)) {
            throw new RuntimeException("Expected 'is greater than'");
        }

        Expression right = parseExpression();

        // Build condition expression
        Expression condition = new BinaryOpNode(left, ">", right);

        // Expect THEN keyword
        if (!match(TokenType.THEN)) {
            throw new RuntimeException("Expected 'then'");
        }

        List<Instruction> body = new ArrayList<>();

        // Skip newline if present
        if (current().getType() == TokenType.NEWLINE) {
            consume();
        }

        // Only single-line body supported
        if (current().getType() == TokenType.SAY) {
            body.add(parsePrint());
        } else if (current().getType() == TokenType.LET) {
            body.add(parseAssign());
        } else {
            throw new RuntimeException("Invalid IF body");
        }

        return new IfInstruction(condition, body);
    }

    //  REPEAT: repeat <countExpr> times <single-line body>
    private Instruction parseRepeat() {
        consume(); // consume REPEAT

        Expression count = parseExpression(); // loop count

        // Expect TIMES keyword
        if (!match(TokenType.TIMES)) {
            throw new RuntimeException("Expected 'times'");
        }

        List<Instruction> body = new ArrayList<>();

        // Skip newline if present
        if (current().getType() == TokenType.NEWLINE) {
            consume();
        }

        // Only single-line body supported
        if (current().getType() == TokenType.SAY) {
            body.add(parsePrint());
        } else if (current().getType() == TokenType.LET) {
            body.add(parseAssign());
        } else {
            throw new RuntimeException("Invalid REPEAT body");
        }

        return new RepeatInstruction(count, body);
    }

    //  EXPRESSIONS (handles + and - precedence level)
    private Expression parseExpression() {
        Expression left = parseTerm();

        // Continue while + or - operators are found
        while (current().getType() == TokenType.PLUS ||
                current().getType() == TokenType.MINUS) {

            String op = consume().getValue();
            Expression right = parseTerm();

            left = new BinaryOpNode(left, op, right);
        }

        return left;
    }

    //  TERMS (handles * and / precedence)
    private Expression parseTerm() {
        Expression left = parseFactor();

        // Continue while * or / operators are found
        while (current().getType() == TokenType.MULTIPLY ||
                current().getType() == TokenType.DIVIDE) {

            String op = consume().getValue();
            Expression right = parseFactor();

            left = new BinaryOpNode(left, op, right);
        }

        return left;
    }

    //  FACTORS (basic units: number, string, variable)
    private Expression parseFactor() {
        Token token = consume();

        switch (token.getType()) {

            case NUMBER:
                return new NumberNode(Double.parseDouble(token.getValue()));

            case STRING:
                return new StringNode(token.getValue());

            case IDENTIFIER:
                return new VariableNode(token.getValue());

            default:
                // Invalid expression element
                throw new RuntimeException("Unexpected token in expression: " + token.getValue());
        }
    }
}