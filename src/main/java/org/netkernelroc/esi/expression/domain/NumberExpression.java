package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class NumberExpression extends BaseExpression {

    public static final String NAME = "Number";

    public NumberExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    Comparable evaluateZeroChildren() {
        return Integer.parseInt(getNode().getValue().toString());
    }
}
