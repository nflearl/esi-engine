package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public abstract class BaseExpression implements ESIExpression {

    protected final ExpressionBuilder eb;
    protected final IHDSNode node;

    protected BaseExpression(ExpressionBuilder builder, IHDSNode curNode) {
        eb = builder;
        node = curNode;
    }

    Comparable evaluateZeroChildren() {
        throw new UnsupportedOperationException();
    }

    Comparable evaluateManyChildren(IHDSNode[] children) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparable evaluate() {
        IHDSNode[] children = node.getChildren();
        switch (children.length) {
            case 0:
                return evaluateZeroChildren();

            // Common case of a wrapper grammar node simply deferring to its child.
            case 1:
                return eb.build(children[0]).evaluate();

            default:
                return evaluateManyChildren(children);
        }
    }

    protected ExpressionBuilder getEb() {
        return eb;
    }

    protected IHDSNode getNode() {
        return node;
    }

    protected boolean convertToBool(Comparable comp) {
        if (comp instanceof Boolean)
            return (Boolean) comp;

        String compValue = comp.toString();
        return Boolean.parseBoolean(compValue);
    }
}
