package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

import java.util.Arrays;

public class OrExpression extends BaseExpression {

    public static final String NAME = "orExp";

    public OrExpression(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    Comparable evaluateManyChildren(IHDSNode[] children) {

        Comparable orResult = eb.build(children[0]).evaluate(children[0].getChildren());
        if (convertToBool(orResult))
            return true;

        // Terminal case
        if (children.length == 1 || children.length < 3)
            return false;

        if (!"OR".equals(children[1].getName()))
            throw new UnsupportedOperationException("Unknown operation: " + children[1].getName());

        return convertToBool(evaluate(Arrays.copyOfRange(children, 2, children.length)));
    }
}
