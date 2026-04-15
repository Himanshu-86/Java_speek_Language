package execution;

import java.util.*;
import parser.*;
import tokenizer.*;

/**
 * Interpreter — the top-level entry point that ties all phases together.
 *
 * A Speek program goes through three pipeline stages before any code runs:
 *
 *   ┌─────────────┐     ┌──────────┐     ┌──────────────────────┐
 *   │  Raw source │ ──► │Tokenizer │ ──► │ List<Token>          │
 *   │  (String)   │     │          │     │ (stream of terminals) │
 *   └─────────────┘     └──────────┘     └──────────┬───────────┘
 *                                                    │
 *                                               ┌────▼──────┐
 *                                               │  Parser   │
 *                                               └────┬──────┘
 *                                                    │
 *                                        ┌───────────▼──────────────┐
 *                                        │  List<Instruction> (AST) │
 *                                        └───────────┬──────────────┘
 *                                                    │
 *                                     ┌──────────────▼──────────────┐
 *                                     │  Execution loop             │
 *                                     │  inst.execute(env) for each │
 *                                     └─────────────────────────────┘
 *
 * Stage 1 — Tokenizer:
 *   Converts the raw source string into a flat list of Token objects
 *   (keywords, identifiers, numbers, operators, punctuation, etc.).
 *
 * Stage 2 — Parser:
 *   Consumes the token list and builds a List<Instruction> — the AST.
 *   Each Instruction is a tree node that may contain nested Expression
 *   objects and further sub-instruction lists (e.g. for if / repeat blocks).
 *
 * Stage 3 — Execution:
 *   A fresh Environment is created for each run() call (no shared state
 *   between calls).  Instructions are executed sequentially; each one
 *   reads/writes variables in the shared Environment as needed.
 *
 * Usage:
 *   new Interpreter().run("x = 5\nprint x");
 */
public class Interpreter {

    /**
     * Runs a complete Speek program from source text.
     *
     * This method is stateless — it creates new Tokenizer, Parser, and
     * Environment instances on every call, so it is safe to call run()
     * multiple times (each invocation is an independent execution).
     *
     * @param source  the full Speek program as a plain string (may contain
     *                newlines, multiple statements, nested blocks, etc.)
     * @throws RuntimeException if a variable is used before it is assigned,
     *         or if the source contains a syntax error detected by the Parser.
     */
    public void run(String source) {
        // ── Stage 1: Tokenize ─────────────────────────────────────────────
        // Break the raw source string into a flat list of Token objects.
        Tokenizer tokenizer = new Tokenizer(source);
        List<Token> tokens = tokenizer.tokenize();

        // ── Stage 2: Parse ────────────────────────────────────────────────
        // Turn the token stream into a structured list of Instructions (AST).
        Parser parser = new Parser(tokens);
        List<Instruction> instructions = parser.parse();

        // ── Stage 3: Execute ──────────────────────────────────────────────
        // Create a fresh variable store for this run, then execute each
        // top-level instruction in the order they appear in the source.
        Environment env = new Environment();

        for (Instruction inst : instructions) {
            inst.execute(env);
        }
    }
}