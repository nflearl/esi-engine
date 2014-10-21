package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class AndExpression extends BaseExpression {

    public static final String NAME = "andExpr";

    private static final int LHS_IDX = 0;
    private static final int AND_IDX = 1;
    private static final int RHS_IDX = 2;

    public AndExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    boolean evaluateManyChildren(IHDSNode[] children) {

        if (children.length != 3)
            throw new IllegalStateException("Expected three parts");


        // Only handling equals case for now.  In the future, we could add more operators.
        if (!"AND".equals(children[AND_IDX].getName()))
            throw new UnsupportedOperationException("Unknown operation: " + children[AND_IDX].getName());
        boolean leftHandSide = eb.build(children[LHS_IDX]).evaluate(children[LHS_IDX].getChildren());
        if (!leftHandSide)
            return false;
        boolean rightHandSide = eb.build(children[RHS_IDX]).evaluate(children[RHS_IDX].getChildren());
        return rightHandSide;
    }
}
