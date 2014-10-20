package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class VariableExpression extends BaseExpression {

    public static final String NAME = "variableExpr";

    // [1] is VAR_ID, then we need to do a lookup.

    public VariableExpression(ExpressionBuilder builder) {
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

    private Comparable getIdValue(IHDSNode child) {
        String variableName = child.getValue().toString();
        return getEb().getEsiContext().retrieveVariable(variableName);
    }

}
