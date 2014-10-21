package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class FunctionExpression extends BaseExpression {

    public static final String NAME = "functionExpr";

    public FunctionExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    public boolean evaluate(IHDSNode[] children) {
        String funcArgs = deriveFunctionArgs(children);
        String funcName = deriveFunctionName(children);
        if ("exists".equals(funcName))
            return !funcArgs.isEmpty();

        if ("is_empty".equals(funcName))
            return funcArgs.isEmpty();

        throw new UnsupportedOperationException("Unknown function name: " + funcName);
    }

    private String deriveFunctionArgs(IHDSNode[] children) {
        for (IHDSNode child : children) {
            if ("functionArgs".equals(child.getName())) {
                ESIExpression expression = getEb().build(child);
                return expression.evaluateToLiteral(child.getChildren()).toString();
            }
        }

        throw new IllegalStateException("Missing functionArgs.");
    }

    private String deriveFunctionName(IHDSNode[] children) {
        for (IHDSNode child : children) {
            if ("FunctionName".equals(child.getName())) {
                ESIExpression expression = getEb().build(child);
                return child.getValue().toString();
            }
        }
        throw new IllegalStateException("Missing function name.");

    }
}