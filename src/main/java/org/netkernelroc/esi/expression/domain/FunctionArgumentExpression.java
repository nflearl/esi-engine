package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class FunctionArgumentExpression extends BaseExpression {

    public static final String NAME = "functionArgs";

    public FunctionArgumentExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    public Comparable evaluate() {

        for (IHDSNode child : getNode().getChildren()) {
            if ("VAR_ID".equals(child.getName()))
                return getEb().getEsiContext().retrieveVariable(child.getValue().toString());
            if ("variableExpr".equals(child.getName())) {
                ESIExpression expr = getEb().build(child);
                return expr.evaluate();
            }
        }

        throw new IllegalStateException("Could not find variableExpr.");

    }
}
