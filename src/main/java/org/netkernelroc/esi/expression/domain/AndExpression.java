package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class AndExpression extends BaseExpression {

    public static final String NAME = "andExpr";

    private static final int LHS_IDX = 0;
    private static final int AND_IDX = 1;
    private static final int RHS_IDX = 2;

    public AndExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    Comparable evaluateManyChildren(IHDSNode[] children) {

        if (children.length != 3)
            throw new IllegalStateException("Expected three parts");

        if (!"AND".equals(children[AND_IDX].getName()))
            throw new UnsupportedOperationException("Unknown operation: " + children[AND_IDX].getName());
        Comparable leftHandSide = eb.build(children[LHS_IDX]).evaluate();
        if (!convertToBool(leftHandSide))
            return false;
        Comparable rightHandSide = eb.build(children[RHS_IDX]).evaluate();
        return convertToBool(rightHandSide);
    }
}
