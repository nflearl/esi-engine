package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class VariableExpression extends BaseExpression {

    public static final String NAME = "variableExpr";

    // [1] is VAR_ID, then we need to do a lookup.

    public VariableExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    public Comparable evaluateToLiteral(IHDSNode[] children) {

        throw new UnsupportedOperationException();

    }
}
