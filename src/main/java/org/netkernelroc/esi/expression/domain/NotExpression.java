package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class NotExpression extends BaseExpression {

    public static final String NAME = "notExpr";

    public NotExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    boolean evaluateManyChildren(IHDSNode[] children) {

        if (children.length != 2)
            throw new IllegalStateException("Expected 2 kids, received: " + children.length);
        if (!"NOT".equals(children[0].getName()))
            throw new UnsupportedOperationException("Unknown operation: " + children[0].getName());
        boolean result = eb.build(children[1]).evaluate(children[1].getChildren());
        return !result;
    }
}