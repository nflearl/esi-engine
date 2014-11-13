package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public abstract class BaseExpression implements ESIExpression {

    protected final ExpressionBuilder eb;

    protected BaseExpression(ExpressionBuilder builder) {
        eb = builder;
    }

    Comparable evaluateZeroChildren() {
        throw new UnsupportedOperationException();
    }

    Comparable evaluateManyChildren(IHDSNode[] children) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparable evaluate(IHDSNode[] children) {
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

    protected ExpressionBuilder getEb() {
        return eb;
    }

    protected boolean convertToBool(Comparable comp) {
        if (comp instanceof Boolean)
            return (Boolean) comp;

        String compValue = comp.toString();
        return Boolean.parseBoolean(compValue);
    }
}
