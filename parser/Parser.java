/**
 * Parser for the Speek language. Transforms a list of tokens into a list of instructions.
 */
package parser;

import execution.*;
import java.util.*;
import tokenizer.*;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token current() {
        return tokens.get(pos);
    }

    private Token consume() {
        return tokens.get(pos++);
    }

    private boolean match(TokenType type) {
        if (current().getType() == type) {
            pos++;
            return true;
        }
        return false;
    }

    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();

        while (current().getType() != TokenType.EOF) {
            // Determine the instruction type based on the starting keyword
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

            else if (current().getType() == TokenType.NEWLINE) {
                consume(); // skip empty lines
            }

            else {
                throw new RuntimeException("Unexpected token: " + current().getValue());
            }
        }

        return instructions;
    }

    // ASSIGN: let <varName> be <expression>
    private Instruction parseAssign() {
        consume(); // LET

        String varName = consume().getValue(); // IDENTIFIER

        if (!match(TokenType.BE)) {
            throw new RuntimeException("Expected 'be'");
        }

        Expression expr = parseExpression();

        return new AssignInstruction(varName, expr);
    }

    // PRINT: say <expression>
    private Instruction parsePrint() {
        consume(); // SAY

        Expression expr = parseExpression();

        return new PrintInstruction(expr);
    }

    // IF: if <expr> is greater than <expr> then <bodyLine>
    private Instruction parseIf() {
        consume(); // IF

        Expression left = parseExpression();

        if (!match(TokenType.GREATER_THAN)) {
            throw new RuntimeException("Expected 'is greater than'");
        }

        Expression right = parseExpression();

        Expression condition = new BinaryOpNode(left, ">", right);

        if (!match(TokenType.THEN)) {
            throw new RuntimeException("Expected 'then'");
        }

        List<Instruction> body = new ArrayList<>();

        // skip newline

        if (current().getType() == TokenType.NEWLINE) {
            consume();
        }

        // single-line body

        if (current().getType() == TokenType.SAY) {
            body.add(parsePrint());
        } else if (current().getType() == TokenType.LET) {
            body.add(parseAssign());
        } else {
            throw new RuntimeException("Invalid IF body");
        }

        return new IfInstruction(condition, body);
    }

    // REPEAT: repeat <countExpr> times <bodyLine>
    private Instruction parseRepeat() {
        consume(); // REPEAT

        Expression count = parseExpression();

        if (!match(TokenType.TIMES)) {
            throw new RuntimeException("Expected 'times'");
        }

        List<Instruction> body = new ArrayList<>();

        // skip newline

        if (current().getType() == TokenType.NEWLINE) {
            consume();
        }

        // single-line body

        if (current().getType() == TokenType.SAY) {
            body.add(parsePrint());
        } else if (current().getType() == TokenType.LET) {
            body.add(parseAssign());
        } else {
            throw new RuntimeException("Invalid REPEAT body");
        }

        return new RepeatInstruction(count, body);
    }

    // EXPRESSIONS

    // EXPRESSIONS: Entry point for parsing expressions with +/- precedence
    private Expression parseExpression() {
        Expression left = parseTerm();

        while (current().getType() == TokenType.PLUS ||
                current().getType() == TokenType.MINUS) {

            String op = consume().getValue();
            Expression right = parseTerm();

            left = new BinaryOpNode(left, op, right);
        }

        return left;
    }

    // TERMS: Handling * and / precedence
    private Expression parseTerm() {
        Expression left = parseFactor();

        while (current().getType() == TokenType.MULTIPLY ||
                current().getType() == TokenType.DIVIDE) {

            String op = consume().getValue();
            Expression right = parseFactor();

            left = new BinaryOpNode(left, op, right);
        }

        return left;
    }

    // FACTORS: Lowest level elements (numbers, variables, strings)
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
                throw new RuntimeException("Unexpected token in expression: " + token.getValue());
        }
    }
}