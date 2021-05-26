# Simple-Math-Parser
Simple Math parser for school assignment
Only Node classes, Parser and Tokenizer implemented by me, all other files was handed out by the teacher

Grammar:
block = ’{’ , stmts , ’}’ ;
stmts = [ assign , stmts ] ;
assign = id , ’=’ , expr , ’;’ ;
expr = term , [ ( ’+’ | ’-’ ) , expr ] ;
term = factor , [ ( ’*’ | ’/’ ) , term ] ;
factor = int | id | ’(’ , expr , ’)’ ;
