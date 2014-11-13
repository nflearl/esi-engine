package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

/*
 * TODO - having this class and the base class VariableOrLiteralExpression is a bit confusing.  Should find a way
 * to consolidate/simplify.
 */
public class VarOrLiteral extends BaseExpression {

    public static final String NAME = "varOrLiteral";

    public VarOrLiteral(ExpressionBuilder builder) {
        super(builder);
    }

    @Override
    public Comparable evaluate(IHDSNode[] children) {
        switch (children.length) {
            case 0:
                return evaluateZeroChildren();

            // Common case of a wrapper grammar node simply deferring to its child.
            case 1:
                return eb.build(children[0]).evaluate(children[0].getChildren());

            default:
                return evaluateManyChildren(children);
        }
    }
}
