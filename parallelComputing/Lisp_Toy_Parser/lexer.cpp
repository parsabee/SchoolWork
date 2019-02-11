/*
 * CIS 431
 * Parallel_Lisp_Compiler
 * Zhibin Zhang, Trace Anderson, Parsa Bagheri, Gabriel Branton
 * Winter, 2018
 *
 * A lexical analyzer for MIT Scheme.
*/

#include <array>
#include <cctype>   // isdigit
#include <cstdlib>  // exit
#include <fstream>
#include <iostream>
#include <iterator> // istreambuf_iterator
#include <string>
#include <utility>
#include <vector>

#include "lexer.h"

std::string what(Token t) {
    static const std::array<std::string, (size_t)Token::COUNT> names =
    {
        "left paren",
        "right paren",
        "cons operator",
        "identifier",
        "integer",
        "real number",
        "stop"
    };
    return (int)t >= 0 && (int)t < names.size() ?
        names[(int)t] : "unknown token";
}

void bad_token() {
    std::cerr << "\033[31;1mUnrecognized token\033[0m" << std::endl;
    exit(1);
}

Lexer::Lexer(std::string && s) try :
    text(std::make_shared<std::string>(std::move(s))),
    pos(text->begin()),
    end(text->end())
{} catch (...) {
    std::cerr << "Lexer failed to allocate text" << std::endl;
    exit(1);
}

namespace
{

bool is_delimiter(char t) {
    switch (t) {
    case '(':
    case ')':
    case ';':
    case '|':
    case '[':
    case ']':
    case '{':
    case '}':
        return true;
    default:
        return false;
    }
}

} // namespace

Token Lexer::lex() {
lex:
    char c;

    /* Scan for next token, ignoring whitespace and comments. */
    for (;;) {
        if (pos == end) {
            return Token::STOP;
        }
        c = *pos;
        pos++;

        if (c == ';') {
            /* Comments ignore until the end of the line. */
            do {
                if (pos == end) {
                    return Token::STOP;
                }
                c = *pos;
                pos++;
            } while (c != '\n' && c != '\b' && c != '\r' && c != 'f');
            continue;
        }

        if (! std::isspace(c)) {
            break;
        }
    }

    /* Lex delimiters. */
    switch(c) {
        case '(':
            return Token::LP;
        case ')':
            return Token::RP;

        /* Cons operator. */
        case '.':
            return Token::CONS;

        default:
            ;
    }

    if (is_delimiter(c)) {
        goto lex;
    }

    if (std::isdigit(c)) { /* Match integers and real numbers. */
        for (;;) {
            if (pos == end) {
                return Token::INT;
            }
            c = *pos;
            if (c == '.') {
                break;
            } else if (! std::isdigit(c)) {
                return Token::INT;
            }
            pos++;
        }
        for (;;) {
            if (pos == end) {
                return Token::REAL;
            }
            c = *pos;
            pos++;
            if (c == '.') {
                bad_token();
            } else if (! std::isdigit(c)) {
                return Token::REAL;
            }
        }
    } else if (c > 32) { /* Lex identifiers. */
        for (;;) {
            if (pos == end) {
                break;
            }
            c = *pos;
            if (c <= 32 || is_delimiter(c)) {
                break;
            }
            pos++;
        }
        return Token::ID;
    } else {
        bad_token();
        throw; /* Never reached. */
    }
}

/* Read from disk to lex. */
Lexer lex_file(std::string path) {
    std::ifstream file(path);
    if (! file) {
        std::cerr << "Failed to open " << path << std::endl;
        exit(1);
    }
    std::string text(
        (std::istreambuf_iterator<char>(file)),
        (std::istreambuf_iterator<char>())
    );
    return Lexer(std::move(text));
}


// int main(int argc, char *argv[]){
//     if (argc<2){
//         return 0;
//     }
//     std::vector<Token> P;
//     Lexer l = lex_file(argv[1]);
//     while(1){
//         P.emplace_back(l.lex());
//         std::cout << what(P.back()) << std::endl;
//         if(P.back() == Token::STOP){
//             break;
//         }
//     }

// }





