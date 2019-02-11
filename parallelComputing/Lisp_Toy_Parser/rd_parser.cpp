/*
 * CIS 431
 * Parallel_Lisp_Compiler (TBB Implementation)
 * Parsa Bagheri
 * Winter, 2018
 *
 * Recursive decent parser for MIT Scheme
 */

#include <iostream>
#include <string>   // getline
#include <vector>
#include <utility>
#include <tbb/parallel_for.h>
#include <tbb/parallel_invoke.h>

#include "rd_parser.hpp"

std::string what(int t) {
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
    return t >= 0 && t < names.size() ?
        names[t] : "unknown token";
}

RD_Parser::RD_Parser() {
    std::string text, s;
    while (std::getline(std::cin, s)) {
        text += std::move(s) + "\n";
    }
    lexer = Lexer(std::move(text));
    token = Token::NONE;
}

RD_Parser::RD_Parser(std::string path) :
    lexer(lex_file(path)),
    token(Token::NONE)
{}

void RD_Parser::perror(std::string info = "") {
    std::cerr << "\033[31;1mParse error:\033[0m " << "\n";
    if (! info.empty()) {
        std::cerr << "  " << info << "\n";
    }
    std::cerr.flush();
    exit(1);
}
int RD_Parser::countParen(std::vector<int> &P, int size){

    /*
    counts the number of left and right parantheses
    return the number of open parantheses,

    no open parantheses if 0 returned
    else (syntax error)
    */
    int numOpenParen = 0;
    tbb::parallel_for(size_t(0), size_t(size), [&](size_t i){
        if(P[i] == (int)Token::LP)
            numOpenParen += 1;
        else if(P[i] == (int)Token::RP)
            numOpenParen -= 1;
        if (numOpenParen < 0)
            return -1;
    });
    return numOpenParen;
}

int count_RP(std::vector<int> &P, int size, int *pos){
    int numRP = 0;
    tbb::parallel_for(size_t(0), size_t(size), [&](size_t i){
        if(P[i] == (int)Token::RP){
            numRP += 1;
            *pos = (int)i; /*store the last occurunce of RP*/
        }
    });
    return numRP;
}

int count_LP(std::vector<int> &P, int size, int *pos){
    int numLP = 0;
    tbb::parallel_for(size_t(0), size_t(size), [&](size_t i){
        if(P[i] == (int)Token::LP){
            numLP += 1;
            *pos = (int)i;
        }
    });
    return numLP;
}

ParseTree *RD_Parser::parse(std::vector<int> &P, int start, int end, int *cur_pos) {
    /*
    input: A list of Tokens, start and end of parsing range
    cur_pos which is used to store the current position in the P when function returns

    If syntactically correct, returns a parse tree;
    else returns 0.
    */
    int pos = start;
    int new_pos = pos;
    if(P.size() == 0){
        perror();
        return 0;
    }

    if(P[start] == (int)Token::STOP){
        std::cout << "reached the end of file" << std::endl;
        return 0;
    }

    if(P[start] != (int)Token::LP){
        perror("file does'nt start with a left paranthesis");
        return 0;
    }

    std::cout << "parsed left paren" << std::endl;
    ParseTree *pt = new ParseTree;

    /*look one index ahead to construct the root of the tree*/
    pos += 1;
    if( P[pos] == (int)Token::LP || 
        P[pos] == (int)Token::RP ||
        P[pos] == (int)Token::STOP ||
        P[pos] == (int)Token::INT ||
        P[pos] == (int)Token::REAL){
        perror("wrong syntax, unexpected left/right paranthesis, eof ..."); /*wrong syntax, unexpected left/right paranthesis, eof ...*/
        return 0;
    }
    else {
        std::cout << "parsed "<< what(P[pos])<< std::endl;
        pt->root = what(P[pos]);
    }

    /*look one more index ahead to construct the left subtree*/
    pos += 1;
    if(P[pos] == (int)Token::LP){
        ParseTree *lt = new ParseTree;
        lt = parse(P, pos, end, &new_pos);
        pos = new_pos;
        pt->LeftTree = lt;
    }
    else if(P[pos] == (int)Token::RP || P[pos] == (int)Token::STOP){
        perror("wrong syntax, unexpected right paranthesis"); /*wrong syntax, unexpected right paranthesis */
        return 0;
    }
    else{
        std::cout << "parsed "<< what(P[pos])<< std::endl;
        ParseTree *lt = new ParseTree;
        lt->root = what(P[pos]);
        pt->LeftTree = lt;
    }

    /*look one more index ahead to construct the right subtree*/
    pos += 1;
    if(P[pos] == (int)Token::LP){
        ParseTree *rt = new ParseTree;
        rt = parse(P, pos, end, &new_pos);
        pos = new_pos;
        pt->RightTree = rt;
    }
    else if(P[pos] == (int)Token::RP || P[pos] == (int)Token::STOP){
        perror(); /*wrong syntax, unexpected right paranthesis */
        return 0;
    }
    else{
        std::cout << "parsed "<< what(P[pos])<< std::endl;
        ParseTree *rt = new ParseTree;
        rt->root = what(P[pos]);
        pt->LeftTree = rt;        
    }

    pos += 1;
    *cur_pos = pos;
    if(P[pos] == (int)Token::RP){
        std::cout << "parsed right paren" << std::endl;
        return pt;
    }
    else{
        perror();
        return 0;
    }
}

void deleteParseTree(ParseTree *pt){
    if(pt->LeftTree != nullptr){
        deleteParseTree(pt->LeftTree);
    }
    if(pt->RightTree != nullptr){
        deleteParseTree(pt->RightTree);
    }
    delete pt;
}

void RD_Parser::parser_helper() {
    std::vector<int> P;
    while(1){
        P.emplace_back((int)lexer.lex());
        if (P.back() == (int)Token::STOP){
            break;
        }
    }
    if(countParen(P, P.size()) != 0)
        perror("Unmatching number of parantheses");
    int pos = -1;

    ParseTree *pt = new ParseTree;
    ParseTree *lt = new ParseTree;
    ParseTree *rt = new ParseTree;
    lt->LeftTree = nullptr;
    rt->LeftTree = nullptr;
    lt->RightTree = nullptr;
    rt->RightTree = nullptr;

    pt->LeftTree = lt;
    pt->RightTree = rt;

    /*find where to split the tokens vector*/
    int mid = P.size()/2;

    int _last_lp;
    int numLP = count_LP(P, mid, &_last_lp);
    numLP -= 1; /*omitted the first paranthesis*/

    int _last_rp = _last_lp;
    int numRP = count_RP(P, mid, &_last_rp);

    int i;
    printf("pos: %d, numLP = %d numRP = %d size = %d\n", mid, numLP, numRP, P.size());
    if(numRP != numLP){
        for(i = _last_rp; i< P.size(); i++){
            if(P[i] == (int)Token::RP)
                numRP += 1;
            else if(P[i] == (int)Token::LP)
                numLP += 1;
        }
        printf("%d\n", i);
    }
    else{
        i=mid;
    }

    if(i == P.size())
        i-=3;

    printf("pos: %d, numLP = %d numRP = %d size = %d\n\n\n", i, numLP, numRP, P.size());

    /*
        divide and conquer TBB pattern:

        tokens vetor will be split at position "i"
        one thread will be in charge of processing 0 - i 
        another thread will be in charge of processing i - P.size()
    */

    if(P[0] != (int)Token::LP){
        perror("file doesn't start with a left paranthesis");
        return;
    }

    std::cout << "parsed left paren "<< std::endl;
    
    if(P[1] == (int)Token::LP || 
        P[1] == (int)Token::RP ||
        P[1] == (int)Token::STOP ||
        P[1] == (int)Token::INT ||
        P[1] == (int)Token::REAL){
        perror("syntax error");
        return;
    }

    pt->root = P[1];
    std::cout << "parsed "<< what(P[1]) << std::endl;
    tbb::parallel_invoke(
        [&]{
            /**********************
            left tree
            **********************/
            if( P[2] == (int)Token::ID||
                P[2] == (int)Token::REAL||
                P[2] == (int)Token::INT){
                std::cout << "parsed "<< what(P[2]) << std::endl;
                lt->root = what(P[2]);
            }
            else if(P[2] == (int)Token::LP){
                lt = parse(P, 2, i, &pos);
            }
            else {
                perror("\"in left tree\"unexpected token");
                return;
            }
        },
        [&]{
            /**********************
            right tree
            **********************/
            if( P[i] == (int)Token::ID ||
                P[i] == (int)Token::REAL ||
                P[i] == (int)Token::INT){
                std::cout << "parsed "<< what(P[i]) << std::endl;
                rt->root = what(P[i]);
            }
            else if(P[i] == (int)Token::LP){
                rt = parse(P, i, P.size()-3, &pos);
            }
            else {
                perror("\"in right tree\" unexpected token");
                return;
            }
        }
        );

    if(P[P.size()-2] != (int)Token::RP){
        perror("file doesn't end with right paranthesis");
        return;
    }
    std::cout << "parsed right parseren" << std::endl;


    deleteParseTree(pt);
}




int main(int argc, char * argv[]) {
    if (argc > 1) { /* Parse files specified in command line arguments. */
        for (int i = 1; i < argc; i++) {
            RD_Parser p(argv[i]);
            p.parser_helper();
        }
    } else { /* Parse from standard input. */
        RD_Parser p;
        p.parser_helper();
    }
}
