/*
 * CIS 431
 * Parallel_Lisp_Compiler
 * Zhibin Zhang, Trace Anderson, Parsa Bagheri, Gabriel Branton
 * Winter, 2018
 *
 * Generates Lisp numerical equation like (+ 1 2) for testing.
 */

#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <sstream>
#include <string>

#define N 20								// max length of expr
std::string pick_sign() {
	return std::string(rand() % 2 ? "+" : "-");
}

std::string pick_int() {
	std::stringstream ss;
	ss << rand() % 100;
	return ss.str();
}

std::string gen_expr() {
	std::string s = std::string("("+pick_sign()+" "+pick_int()+" "+pick_int()+")");
	int r = rand() % N;
	if (r > 0 && r <= N/2)
			return "("+pick_sign()+" "+gen_expr()+" "+pick_int()+")";
	else if (r > N/2)
			return "("+pick_sign()+" "+pick_int()+" "+gen_expr()+")";
	return s;										// when r = 0
}

int main() {
	std::srand(std::time(0));
	printf("%s\n", gen_expr().c_str());

	return 0;
}
