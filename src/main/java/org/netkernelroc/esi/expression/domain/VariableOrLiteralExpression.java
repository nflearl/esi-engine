package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public abstract class VariableOrLiteralExpression extends BaseExpression {

    public VariableOrLiteralExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    public Comparable evaluate() {

        IHDSNode[] children = getNode().getChildren();

        for (IHDSNode child : children) {
            if ("VAR_ID".equals(child.getName()))
              return getIdValue(child);

            if ("varWithArgQuoted".equals(child.getName())) {
                ESIExpression expr = getEb().build(child);
                return expr.evaluate();
            }
        }

        // TODO - do this one a little better, it's a little brittle assuming that
        // the middle element is the true expression.
        if (children.length == 3) {
            ESIExpression expr = getEb().build(children[1]);
            return expr.evaluate();
        }

        return "";
    }

    protected abstract Comparable getIdValue(IHDSNode child);
}
