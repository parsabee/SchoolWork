/*
 * CIS 431
 * Parallel_Lisp_Compiler
 * Zhibin Zhang, Trace Anderson, Parsa Bagheri, Gabriel Branton
 * Winter, 2018
 *
 * A lexical analyzer for MIT Scheme.
 */

#ifndef _LISP_LEXER_
#define _LISP_LEXER_

#include <iostream>
#include <memory>
#include <string>

/*
 * LP: left parentheses
 * RP: right parentheses
 * CONS: cons operator
 * ID: identifier
 * INT: integer
 * REAL: real number
 * STOP: <EOF>
 */
enum class Token : char {
    NONE = -1,

    LP,
    RP,
    CONS,
    ID,
    INT,
    REAL,
    STOP,

    COUNT
};

/* Get plaintext token kind. */
std::string what(Token t);

/* cerr unrecognized token and exit */
void bad_token(void);

struct Lexer {
    Lexer() = default;
    Lexer(std::string &&);
    /* Return the next token, or STOP if the stream was exhausted. */
    Token lex();
private:
    std::shared_ptr<std::string>    text;
    std::string::const_iterator     pos, end;
};

/* Read from disk to lex. */
Lexer lex_file(std::string path);

#endif
