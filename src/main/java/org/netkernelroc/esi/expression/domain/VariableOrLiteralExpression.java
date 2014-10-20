package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public abstract class VariableOrLiteralExpression extends BaseExpression {

    public VariableOrLiteralExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    public Comparable evaluateToLiteral(IHDSNode[] children) {

        for (IHDSNode child : children) {
            if ("VAR_ID".equals(child.getName()))
              return getIdValue(child);
        }

        return "";
    }

    protected abstract Comparable getIdValue(IHDSNode child);
}