package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public abstract class BaseExpression implements ESIExpression {

    protected final ExpressionBuilder eb;

    protected BaseExpression(ExpressionBuilder builder) {
        eb = builder;
    }

    boolean evaluateZeroChildren() {
        throw new UnsupportedOperationException();
    }

    boolean evaluateManyChildren(IHDSNode[] children) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean evaluate(IHDSNode[] children) {
        switch (children.length) {
            case 0:
                return evaluateZeroChildren();

            // Common case of a wrapper grammar node simply deferring to its child.
            case 1:
                return eb.build(children[0]).evaluate(children[0].getChildren());

            default:
                return evaluateManyChildren(children);
        }
    }

    @Override
    public Comparable evaluateToLiteral(IHDSNode[] children) {
        return evaluate(children);
    }
}
