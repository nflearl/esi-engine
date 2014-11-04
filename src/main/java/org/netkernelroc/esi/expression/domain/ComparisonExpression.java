package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComparisonExpression extends BaseExpression {

    public static final String NAME = "comparisonExpr";
    public static final String TRIPLE_QUOTE = "'''";
    private static final int LHS_IDX = 0;
    private static final int COMPARE_OP_IDX = 1;
    private static final int RHS_IDX = 2;

    public ComparisonExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    boolean evaluateManyChildren(IHDSNode[] children) {

        if (children.length != 3)
            throw new IllegalStateException("Expected three parts");


        // Only handling equals case for now.  In the future, we could add more operators.
        if ("EQUALS".equals(children[COMPARE_OP_IDX].getName())) {
            return equalComparison(children);
        }

        if ("MATCH_FUNC".equals(children[COMPARE_OP_IDX].getName()))
            return matches(children);

        throw new UnsupportedOperationException("Unknown operation: " + children[COMPARE_OP_IDX].getName());

    }

    private boolean equalComparison(IHDSNode[] children) {
        Comparable leftHandSide = eb.build(children[LHS_IDX]).evaluateToLiteral(children[LHS_IDX].getChildren());
        Comparable rightHandSide = eb.build(children[RHS_IDX]).evaluateToLiteral(children[RHS_IDX].getChildren());
        return leftHandSide.equals(rightHandSide);
    }

    private boolean matches(IHDSNode[] children) {
        Comparable leftHandSide = eb.build(children[LHS_IDX]).evaluateToLiteral(children[LHS_IDX].getChildren());
        String matchValue = children[RHS_IDX].getChildren()[0].getValue().toString();
        if (matchValue.startsWith("'''"))
            matchValue = matchValue.substring(TRIPLE_QUOTE.length());
        if (matchValue.endsWith(TRIPLE_QUOTE))
            matchValue = matchValue.substring(0, matchValue.length() - TRIPLE_QUOTE.length());
        // TODO - use a resource to give the "matches" answer, allowing for caching of similar requests
        // as the pattern and the leftHandSide will be a fairly small and constant set.
        Pattern pat = Pattern.compile(matchValue);
        Matcher matcher = pat.matcher(leftHandSide.toString());
        return matcher.matches();
    }
}
