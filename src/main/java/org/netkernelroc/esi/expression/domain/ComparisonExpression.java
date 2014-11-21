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

    public ComparisonExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    Comparable evaluateManyChildren(IHDSNode[] children) {

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
        Comparable leftHandSide = eb.build(children[LHS_IDX]).evaluate();
        Comparable rightHandSide = eb.build(children[RHS_IDX]).evaluate();
        return leftHandSide.equals(rightHandSide);
    }

    private boolean matches(IHDSNode[] children) {
        Comparable leftHandSide = eb.build(children[LHS_IDX]).evaluate();
        String matchValue = extractMatchValue(children[RHS_IDX]);
        return computeMatches(leftHandSide, matchValue);
    }

    private boolean computeMatches(Comparable leftHandSide, String matchValue) {
        // TODO - use a resource to give the "matches" answer, allowing for caching of similar requests
        // as the pattern and the leftHandSide will be a fairly small and constant set.
        Pattern pat = Pattern.compile(matchValue);
        Matcher matcher = pat.matcher(leftHandSide.toString());
        boolean retMatches = matcher.matches();
        if (retMatches)
            saveMatches(matcher);
        return retMatches;
    }

    private void saveMatches(Matcher matcher) {
        String groupMatches[] = new String[matcher.groupCount()];
        for (int idx = 0; idx < groupMatches.length; idx++)
            groupMatches[idx] = matcher.group(idx);
        getEb().getEsiContext().saveMatchValues(groupMatches);
    }

    private String extractMatchValue(IHDSNode matchValueWrapperNode) {
        String matchValue = matchValueWrapperNode.getChildren()[0].getValue().toString();
        if (matchValue.startsWith("'''"))
            matchValue = matchValue.substring(TRIPLE_QUOTE.length());
        if (matchValue.endsWith(TRIPLE_QUOTE))
            matchValue = matchValue.substring(0, matchValue.length() - TRIPLE_QUOTE.length());
        return matchValue;
    }
}
