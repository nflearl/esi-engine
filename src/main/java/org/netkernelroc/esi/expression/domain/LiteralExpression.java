package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class LiteralExpression extends VariableOrLiteralExpression {

    public static final String NAME = "literalExpr";

    public LiteralExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    protected Comparable getIdValue(IHDSNode child) {
        return child.getValue().toString();
    }
}
