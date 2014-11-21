package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class FunctionArgumentExpression extends BaseExpression {

    public static final String NAME = "functionArgs";

    public FunctionArgumentExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    Comparable evaluateZeroChildren() {
        return "";
    }

    @Override
    Comparable evaluateManyChildren(IHDSNode[] children) {
        // single argument bracketed by parenthesis
        if (children.length == 3)
        {
            return getEb().build(children[1]).evaluate();
        }

        if (children.length % 2 == 0)
            throw new IllegalStateException("Expected odd number of children: " + children.length);

        FunctionArgs retArg = new FunctionArgs(children.length / 2);
        int idx = 0;
        for (IHDSNode child : children) {
            // Add the odd ones, the evens are syntax tokens
            if (idx++ % 2 == 1) {
                retArg.addArg(getEb().build(child).evaluate());
            }
        }

        return retArg;
    }
}
