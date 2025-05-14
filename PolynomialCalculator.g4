grammar PolynomialCalculator;

options {
    language = Java;
}

start returns [ast.Node node]
    : { ast.NodeList nodeList = new ast.NodeList(); } ( (line { nodeList.add($line.node); } ) ? COMMENT? LF)* EOF { $node = nodeList; }
    ;

line returns [ast.Node node]
    : ID KW_ASSIGN expr KW_SEMICOL { $node = new ast.MemoryAssign($ID.text, $expr.node); }
    | { ast.NodeList nodeList = new ast.NodeList(); }
        TYPE first=ID {
            ast.Node firstNode = $TYPE.text.equals("number")
                ? new ast.MemoryAssign($first.text, new ast.PolynomialLiteral(ast.Polynomial.fromNumber(0.0)))
                : new ast.MemoryAssign($first.text, new ast.PolynomialLiteral(new ast.Polynomial()));
            nodeList.add(firstNode);
        }
        (KW_COMMA next=ID {
            ast.Node nextNode = $TYPE.text.equals("number")
                ? new ast.MemoryAssign($next.text, new ast.PolynomialLiteral(ast.Polynomial.fromNumber(0.0)))
                : new ast.MemoryAssign($next.text, new ast.PolynomialLiteral(new ast.Polynomial()));
            nodeList.add(nextNode);
        })* KW_SEMICOL
        { $node = nodeList; }
    | SHOW KW_PARENOPEN expr KW_PARENCLOSE KW_SEMICOL { $node = new ast.Show($expr.node); }
    ;

expr returns [ast.Node node]
    : first=addop { $node = $first.node; }
        (OPADD next=addop {
           $node = new ast.BinaryOperation($OPADD.text, $node, $next.node);
        })*
    ;

addop returns [ast.Node node]
    : first=mulop { $node = $first.node; }
        (OPMUL next=mulop {
           $node = new ast.BinaryOperation($OPMUL.text, $node, $next.node);
        })*
    ;

mulop returns [ast.Node node]
    : first=fct { $node = $first.node; }
        (OPMUL next=fct {
            $node = new ast.BinaryOperation($OPMUL.text, $node, $next.node);
        })*
    ;

fct returns [ast.Node node]
    : monomial { $node = $monomial.node; }
    | polyExpr { $node = $polyExpr.node; }
    | base=fct KW_SQRBRACKOPEN expr KW_SQRBRACKCLOSE { $node = new ast.Evaluation($base.node, $expr.node); }
    | NUMBER { $node = new ast.NumberLiteral(Double.parseDouble($NUMBER.text)); }
    | ID { $node = new ast.MemoryAccess($ID.text); }
    | KW_PARENOPEN expr KW_PARENCLOSE { $node = $expr.node; }
    ;

polyExpr returns [ast.Node node]
    : KW_POLSTART expr KW_POLEND { $node = $expr.node; }
    ;

monomial returns [ast.Node node]
    : coeff=(INT | NUMBER)? KW_X (KW_EXP exp=INT)? {
        double coef = $coeff != null ? Double.parseDouble($coeff.text) : 1.0;
        int expVal = $exp != null ? Integer.parseInt($exp.text) : 1;
        ast.Polynomial poly = new ast.Polynomial();
        poly.addTerm(expVal, coef);
        $node = new ast.PolynomialLiteral(poly);
    }
    | num=(INT | NUMBER) {
        ast.Polynomial poly = ast.Polynomial.fromNumber(Double.parseDouble($num.text));
        $node = new ast.PolynomialLiteral(poly);
    }
    ;


LF                  : '\n' ;
WS                  : [ \t\r]+ ->skip ;
INT                 : [0-9]+ ;
NUMBER              : [0-9]+('.' [0-9]+)? ;
OPADD               : '+' | '-' ;
OPMUL               : '*' | '/' | '%' ;
TYPE                : 'number' | 'polynom' ;
SHOW                : 'show' ;
KW_X                : 'x' | 'X' ;
KW_POLSTART         : '<' ;
KW_POLEND           : '>' ;
KW_EXP              : '^' ;
KW_ASSIGN           : '=' ;
KW_SEMICOL          : ';' ;
KW_COMMA            : ',' ;
KW_PARENOPEN        : '(' ;
KW_PARENCLOSE       : ')' ;
KW_SQRBRACKOPEN     : '[' ;
KW_SQRBRACKCLOSE    : ']' ;
ID                  : [a-zA-Z_]+[a-zA-Z_0-9]* ;
COMMENT             : '#' (~[\n])* ;
