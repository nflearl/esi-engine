package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

import java.util.ArrayList;
import java.util.List;

public class VariableExpression extends VariableOrLiteralExpression {

    public static final String NAME = "variableExpr";
    public static final String ALT_NAME = "VAR_ID";

    // [1] is VAR_ID, then we need to do a lookup.

    public VariableExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    Comparable evaluateManyChildren(IHDSNode[] children) {
        List<IHDSNode> remainders = new ArrayList<IHDSNode>(children.length);
        // Throw out parens and try again.
        for (IHDSNode child : children) {
            if (child.getName().contains("PAREN"))
                continue;

            remainders.add(child);
        }

        if (remainders.size() != 1)
            throw new UnsupportedOperationException("Don't know what to do with remainders of size: " + remainders.size());

        IHDSNode child = remainders.get(0);
        ESIExpression expr = getEb().build(child);
        return expr.evaluate(child.getChildren());
    }

    @Override
    protected Comparable getIdValue(IHDSNode child) {
        String variableName = child.getValue().toString();
        return getEb().getEsiContext().retrieveVariable(variableName);
    }

}
