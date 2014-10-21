package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class VariableExpression extends VariableOrLiteralExpression {

    public static final String NAME = "variableExpr";
    public static final String ALT_NAME = "VAR_ID";

    // [1] is VAR_ID, then we need to do a lookup.

    public VariableExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    protected Comparable getIdValue(IHDSNode child) {
        String variableName = child.getValue().toString();
        return getEb().getEsiContext().retrieveVariable(variableName);
    }

}
