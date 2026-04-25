grammar Glwssa;

// Glwssa Parser Rules

// 1. The program must have a name variables a start statements and an end
program : PROGRAM_KW ID declarations START_KW statement* END_KW ;

// 2. Variable Declaration Block: Starts with "ΜΕΤΑΒΛΗΤΕΣ" followed by >=0 declarations
declarations : VARS_KW var_decl* ;

// 3. A single declaration
var_decl : TYPE_KW ID (',' ID)* ;

// 4. Statements: Supported : assignment, printing
statement : assignment
          | print_stmnt ;

// 5. Assignment
assignment : ID ASSIGN expr ;

// 6. Print Statement
print_stmnt : PRINT_KW print_item (',' print_item)* ;

print_item : expr | STRING ;

// 7. Expressions
expr : expr op=(MULT | DIV_KW | MOD_KW | SLASH) expr # MathExpr
     | expr op=(PLUS | MINUS) expr                   # MathExpr
     | expr op=(EQ | NEQ | LT | GT | LTE | GTE) expr # RelationalExpr
     | NUMBER                                        # NumberExpr
     | ID                                            # IdExpr
     | TRUE_KW                                       # BoolExpr
     | FALSE_KW                                      # BoolExpr
     | '(' expr ')'                                  # ParenExpr
     ;

// Vocabulary

//Keywords
PROGRAM_KW : 'ΠΡΟΓΡΑΜΜΑ' ;
VARS_KW    : 'ΜΕΤΑΒΛΗΤΕΣ' ;
START_KW   : 'ΑΡΧΗ' ;
END_KW     : 'ΤΕΛΟΣ_ΠΡΟΓΡΑΜΜΑΤΟΣ' ;
PRINT_KW   : 'ΓΡΑΨΕ' ;

//Data types
TYPE_KW : 'ΑΚΕΡΑΙΕΣ:' | 'ΠΡΑΓΜΑΤΙΚΕΣ:' | 'ΛΟΓΙΚΕΣ:' | 'ΧΑΡΑΚΤΗΡΕΣ:' ;

//Boolean Constants
TRUE_KW    : 'ΑΛΗΘΗΣ' ;
FALSE_KW   : 'ΨΕΥΔΗΣ' ;

// Operators
ASSIGN     : '<-' ;
PLUS       : '+' ;
MINUS      : '-' ;
MULT       : '*' ;
SLASH      : '/' ;
DIV_KW     : 'DIV' ;
MOD_KW     : 'MOD' ;
EQ         : '=' ;
NEQ        : '<>' ;
LT         : '<' ;
GT         : '>' ;
LTE        : '<=' ;
GTE        : '>=' ;

// Program/Variable naming rules
ID : [a-zA-Zα-ωΑ-Ω][a-zA-Zα-ωΑ-Ω0-9_]* ;

// Numbers
NUMBER : [0-9]+ ('.' [0-9]+)? ;

// Strings
STRING : '"' .*? '"' ;

// Ignore Comments
COMMENT : '!' ~[\r\n]* -> skip ;

// Ignore White Space
WS : [ \t\r\n]+ -> skip ;
