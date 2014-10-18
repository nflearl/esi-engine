package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

import java.util.HashMap;
import java.util.Map;

public class ExpressionBuilder {

    private static final Map<String, ESIExpression> exprMap = new HashMap<String, ESIExpression>(89);

    public ExpressionBuilder() {
    }

    static {
        exprMap.put(BooleanExpression.NAME, new BooleanExpression());
        exprMap.put(ComparisonExpression.NAME, new ComparisonExpression());
        exprMap.put(VarOrLiteral.NAME, new VarOrLiteral());
        exprMap.put(VariableExpression.NAME, new VariableExpression());
    }

    public ESIExpression build(IHDSNode root) {
        ESIExpression retExpr = exprMap.get(root.getName());
        return (retExpr == null) ? new DefaultExpressionHandler() : retExpr;
    }
}
