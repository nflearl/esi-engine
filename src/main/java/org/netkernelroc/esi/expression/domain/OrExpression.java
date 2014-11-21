package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public class OrExpression extends BaseExpression {

    public static final String NAME = "orExp";

    public OrExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    Comparable evaluateManyChildren(IHDSNode[] children) {

        int idx = 0;
        int count = children.length;
        while (count > 0) {
            Comparable orResult = eb.build(children[idx]).evaluate();
            if (convertToBool(orResult))
                return true;

            String childName = children[idx + 1].getName();
            if (count > 1 && !"OR".equals(childName))
                throw new UnsupportedOperationException("Unknown operation: " + childName);

            idx += 2;
            count -= 2;
        }

        return false;
    }
}
