grammar esi_exp;

/* This will be the entry point of our parser. */
eval
    :    booleanExp
    ;

/* Addition and subtraction have the lowest precedence. */
booleanExp
    :   compoundExpr
    |	simpleExpr
    ;

compoundExpr
    :   simpleExpr AND simpleExpr
    ;
    
simpleExpr
    :   functionExpr
    |   comparisonExpr
    ;

comparisonExpr
    :   varOrLiteral '==' varOrLiteral
    ;

varOrLiteral
    :   literalExpr
    |   variableExpr
    ;

literalExpr : QUOTE VAR_ID QUOTE ;
variableExpr
    : OPEN_VAR VAR_ID CLOSE_PAREN           // $(HTTP_HOST)
    | OPEN_VAR varWithArg CLOSE_PAREN    // $(QUERY_STRING{param})
    | OPEN_VAR varWithArgQuoted CLOSE_PAREN    // $(QUERY_STRING{'param'})
    ;

varWithArg : VAR_ID OPEN_ARG VAR_ID CLOSE_ARG ;
varWithArgQuoted : VAR_ID OPEN_QUOTED_ARG VAR_ID CLOSE_QUOTED_ARG ;

functionExpr
    : '$' FunctionName functionArgs;


FunctionName :
    'exists'
     | 'is_empty'
     ;

functionArgs
    :   '()'
    |  OPEN_PAREN VAR_ID CLOSE_PAREN
    |  OPEN_PAREN variableExpr CLOSE_PAREN
    ;

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