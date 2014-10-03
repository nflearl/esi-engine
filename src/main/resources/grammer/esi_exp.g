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
variableExpr : OPEN_VAR VAR_ID CLOSE_PAREN ;

functionExpr
    : '$' FunctionName FunctionArgs;


FunctionName :
    'exists'
     | 'is_empty'
     ;

FunctionArgs
    :   '()'
    |  '(' VAR_ID ')'
    ;

QUOTE       : '\'' ;
OPEN_VAR  : '$(' ;
OPEN_PAREN : '(' ;
CLOSE_PAREN : ')' ;
VAR_ID      : ('a'..'z'|'A'..'Z')+ ;      // match identifiers

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