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
    :   LITERAL_VALUE
    |   VARIABLE
    ;

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

LITERAL_VALUE : '\'' VAR_ID '\'' ;
VARIABLE :   '$(' VAR_ID ')' ;
VAR_ID  :   ('a'..'z'|'A'..'Z')+ ;      // match identifiers

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