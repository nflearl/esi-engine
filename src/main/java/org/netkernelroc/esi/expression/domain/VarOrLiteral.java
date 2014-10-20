package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class VarOrLiteral extends BaseExpression {

    public static final String NAME = "varOrLiteral";

    public VarOrLiteral(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    public Comparable evaluateToLiteral(IHDSNode[] children) {
        switch (children.length) {
            case 0:
                return evaluateZeroChildren();

            // Common case of a wrapper grammar node simply deferring to its child.
            case 1:
                return eb.build(children[0]).evaluateToLiteral(children[0].getChildren());

            default:
                return evaluateManyChildren(children);
        }
    }
}
