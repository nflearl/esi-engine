package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class BooleanExpression extends BaseExpression {

    public static final String NAME = "booleanExp";

    protected BooleanExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }
}
