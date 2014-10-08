grammar esi_exp2;

/* This will be the entry point of our parser. */
eval
    :    booleanExp
    ;

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
    :   term (EQUALS term)*
    ;

term 
  :  INTEGER  
  |  IDENT       
  |  '(' booleanExp ')'
  ;

AND     :  'and';
OR      :  'or';
NOT     :  'not';
EQUALS	:  '==';
IDENT   :  LETTER LETTER*;
INTEGER :  DIGIT+;
WS      :  (' ' | '\n' | '\r' | '\t')+  { $channel = HIDDEN; };

fragment DIGIT   : '0'..'9';
fragment LETTER  : ('a'..'z' | 'A'..'Z');

