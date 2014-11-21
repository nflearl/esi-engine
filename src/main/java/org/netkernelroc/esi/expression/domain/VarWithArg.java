package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;
import org.netkernelroc.esi.parsing.VariableSubstituter;

import java.util.ArrayList;
import java.util.List;

public class VarWithArg extends BaseExpression {

    public static final String NAME = "varWithArg";
    public static final String ALT_NAME = "varWithArgQuoted";

    private final VariableSubstituter vs;

    public VarWithArg(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);

        vs = new VariableSubstituter(builder.getEsiContext());
    }

    @Override
    public Comparable evaluate() {
        IHDSNode[] children = getNode().getChildren();
        List<String> vars = new ArrayList<String>(children.length);

        for (IHDSNode child : children) {
             if ("VAR_ID".equals(child.getName()))
                 vars.add(child.getValue().toString());
        }

        if (vars.size() != 2)
            throw new IllegalStateException("Expecting a variable and it's argument.  Instead, got this many: " + vars.size());

        @SuppressWarnings("UnnecessaryLocalVariable")
        String retStr = vs.resolve(vars.get(0), vars.get(1));
        return retStr;
    }
}
