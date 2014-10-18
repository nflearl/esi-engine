package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class ComparisonExpression extends BaseExpression {

    public static final String NAME = "comparisonExpr";
    private static final int LHS_IDX = 0;
    private static final int COMPARE_OP_IDX = 1;
    private static final int RHS_IDX = 2;

    public ComparisonExpression() {
    }

    @Override
    boolean evaluateManyChildren(IHDSNode[] children) {

        if (children.length != 3)
            throw new IllegalStateException("Expected three parts");


        // Only handling equals case for now.  In the future, we could add more operators.
        if (!"EQUALS".equals(children[COMPARE_OP_IDX].getName()))
            throw new UnsupportedOperationException("Unknown operation: " + children[COMPARE_OP_IDX].getName());
        Comparable leftHandSide = eb.build(children[LHS_IDX]).evaluateToLiteral(children[LHS_IDX].getChildren());
        Comparable rightHandSide = eb.build(children[RHS_IDX]).evaluateToLiteral(children[RHS_IDX].getChildren());
        return leftHandSide.equals(rightHandSide);
    }
}
