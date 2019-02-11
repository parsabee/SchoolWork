/*
 * CIS 431
 * Parallel_Lisp_Compiler (TBB Implementation)
 * Parsa Bagheri
 * Winter, 2018
 *
 * Recursive decent parser for MIT Scheme
 */

 #ifndef _LISP_RD_PARSER_
 #define _LISP_RD_PARSER_

#include "lexer.h"

#include <string>

struct ParseTree{
    std::string root;
    ParseTree *LeftTree;
    ParseTree *RightTree;
};

struct RD_Parser {
    RD_Parser(); /* By default, input is read to completion from standard in. */
    RD_Parser(std::string path); /* Parse a file. */

    /* Indicate an error state and exit. */
    void perror(std::string);
    /* Look ahead one token. */
    ParseTree *parse(std::vector<int> &P, int start, int end, int *cur_pos);

    void parser_helper();
    int countParen(std::vector<int> &P, int size);

private:
    Lexer lexer;
    Token token;
};

#endif // ifndef _LISP_RD_PARSER_
