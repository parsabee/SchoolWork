CXX=g++ -std=c++1z
bins=equ_gen rd_parser

all: $(bins)

test:
		./equ_gen | ./rd_parser

lexer.o: lexer.cpp lexer.h
		$(CXX) -o lexer.o -c lexer.cpp

rd_parser.o: rd_parser.cpp rd_parser.hpp
	$(CXX) -o rd_parser.o -c rd_parser.cpp -ltbb

rd_parser: rd_parser.o lexer.o
	$(CXX) -o rd_parser lexer.o rd_parser.o -ltbb

# tests
equ_gen: equ_gen.cpp
		$(CXX) -o equ_gen equ_gen.cpp

clean:
		rm *.o $(bins)
