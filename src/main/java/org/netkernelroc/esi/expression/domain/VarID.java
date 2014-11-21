package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class VarID extends BaseExpression {

    public static final String NAME = "VAR_ID";

    public VarID(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    Comparable evaluateZeroChildren() {
        // Render out the variable
        return getEb().getEsiContext().retrieveVariable(getNode().getValue().toString());
    }
}
