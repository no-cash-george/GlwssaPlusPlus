grammar Glwssa;

// Glwssa Parser Rules

// 1. The program must have a name variables a start statements and an end
program : PROGRAM_KW ID declarations START_KW statement* END_KW ;

// 2. Variable Declaration Block: Starts with "ΜΕΤΑΒΛΗΤΕΣ" followed by >=0 declarations
declarations : VARS_KW var_decl* ;

// 3. A single declaration
var_decl : TYPE_KW ID (',' ID)* ;

// 4. Statements: Supported : assignment, printing, user input, if statements, switch statements
statement : assignment
          | print_stmnt
          | read_stmnt
          | if_stmnt
          | select_stmnt;

// 5. Assignment
assignment : ID ASSIGN expr ;

// 6. Print Statement
print_stmnt : PRINT_KW print_item (',' print_item)* ;

print_item : expr | STRING ;

// 7. Read Statement
read_stmnt : READ_KW ID (',' ID)* ;

// 8. If Statements
if_stmnt : IF_KW expr THEN_KW statement* else_if_block* else_block? END_IF_KW;

else_if_block : ELSE_IF_KW expr THEN_KW statement* ;

else_block : ELSE_KW statement* ;

// 9. Select Staments (Switch in java)
select_stmnt : SELECT_KW expr case_block* default_block? END_SELECT_KW ;

case_block : CASE_KW expr (',' expr)* statement* ;

default_block : CASE_KW ELSE_KW statement* ;

// Expressions
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
READ_KW    : 'ΔΙΑΒΑΣΕ' ;

IF_KW      : 'ΑΝ' ;
THEN_KW    : 'ΤΟΤΕ' ;
ELSE_IF_KW : 'ΑΛΛΙΩΣ_ΑΝ' ;
ELSE_KW    : 'ΑΛΛΙΩΣ' ;
END_IF_KW  : 'ΤΕΛΟΣ_ΑΝ' ;

SELECT_KW : 'ΕΠΙΛΕΞΕ' ;
CASE_KW : 'ΠΕΡΙΠΤΩΣΗ' ;
END_SELECT_KW : 'ΤΕΛΟΣ_ΕΠΙΛΟΓΩΝ' ;

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
