grammar esi_exp;

/* This will be the entry point of our parser. */
eval
    :    booleanExp
    ;

/* Addition and subtraction have the lowest precedence. */
booleanExp
    :  orExp
    ;

orExp
    :  andExpr (OR andExpr)*
    ;

andExpr
    :  notExpr (AND notExpr)*
    ;

notExpr
    :  NOT comparisonExpr
    |  comparisonExpr
    ;

comparisonExpr
    :   varOrLiteral (EQUALS varOrLiteral | MATCH_FUNC matchValue)*
    ;

varOrLiteral
    :   functionExpr
    |   literalExpr
    |   variableExpr
    ;

literalExpr : QUOTE VAR_ID QUOTE
    | VAR_ID ;
variableExpr
    : OPEN_VAR VAR_ID CLOSE_PAREN           // $(HTTP_HOST)
    | OPEN_VAR varWithArg CLOSE_PAREN    // $(QUERY_STRING{param})
    | OPEN_VAR varWithArgQuoted CLOSE_PAREN    // $(QUERY_STRING{'param'})
    | OPEN_PAREN booleanExp CLOSE_PAREN
    ;

varWithArg : VAR_ID OPEN_ARG (VAR_ID | Number) CLOSE_ARG ;
varWithArgQuoted : VAR_ID OPEN_QUOTED_ARG VAR_ID CLOSE_QUOTED_ARG ;

matchValue : MATCH_VALUE_REGEX ;

functionExpr
    : '$' FunctionName functionArgs;


FunctionName :
    'exists'
     | 'is_empty'
     | 'substr'
     ;

functionArgs
    :   '()'
    |  OPEN_PAREN functionArgumentElem (',' functionArgumentElem)* CLOSE_PAREN
    ;

functionArgumentElem
    	: VAR_ID
    	| Number
    	| variableExpr
    	| literalExpr
    	;

EQUALS 	:	 '==' ;
MATCH_FUNC     : 'matches' ;
TRIPLE_QUOTE : '\'\'\'' ;
QUOTE       : '\'' ;
OPEN_VAR  : '$(' ;
OPEN_PAREN : '(' ;
CLOSE_PAREN : ')' ;
OPEN_ARG : '{' ;
CLOSE_ARG : '}' ;
OPEN_QUOTED_ARG : '{\'' ;
CLOSE_QUOTED_ARG : '\'}' ;
VAR_ID      : ('a'..'z'|'A'..'Z'|'_')+ ;      // match identifiers

AND : '&&' | '&' ;
OR : '|' | '||' ;
NOT : '!' ;

/* A number: can be an integer value */
Number
    :    ('0'..'9')+
    ;

WS
    :   (
             ' '
        |    '\r'
        |    '\t'
        |    '\u000C'
        |    '\n'
        )
            {
                skip();
            }
    ;

MATCH_VALUE_REGEX : TRIPLE_QUOTE ~(QUOTE)* TRIPLE_QUOTE;