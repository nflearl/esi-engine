package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

import java.util.Comparator;

public class FunctionExpression extends BaseExpression {

    public static final String NAME = "functionExpr";

    public FunctionExpression(ExpressionBuilder builder, IHDSNode curNode) {
        super(builder, curNode);
    }

    @Override
    public Comparable evaluate() {
        IHDSNode[] children = getNode().getChildren();
        Comparable funcArgs = deriveFunctionArgs(children);
        String funcName = deriveFunctionName(children);
        // TODO - really incorrect, we currently don't allow empty esi:assign.
        if ("exists".equals(funcName))
            return !funcArgs.toString().isEmpty();

        if ("is_empty".equals(funcName))
            return funcArgs.toString().isEmpty();

        if ("substr".equals(funcName))
            return deriveSubstring((FunctionArgs) funcArgs);

        if ("lower".equals(funcName))
            return funcArgs.toString().toLowerCase();

        if ("replace".equals(funcName))
            return deriveReplace((FunctionArgs) funcArgs);

        throw new UnsupportedOperationException("Unknown function name: " + funcName);
    }

    private Comparable deriveFunctionArgs(IHDSNode[] children) {
        for (IHDSNode child : children) {
            if ("functionArgs".equals(child.getName())) {
                ESIExpression expression = getEb().build(child);
                return expression.evaluate();
            }
        }

        throw new IllegalStateException("Missing functionArgs.");
    }

    private String deriveFunctionName(IHDSNode[] children) {
        for (IHDSNode child : children) {
            if ("FunctionName".equals(child.getName())) {
                return child.getValue().toString();
            }
        }
        throw new IllegalStateException("Missing function name.");

    }

    private String deriveSubstring(FunctionArgs args) {
        int len = args.getArgs().length;
        if (len != 2 && len != 3)
            throw new IllegalStateException("Substring must have 2 or 3 arguments.  Received: " + len);
        String source = args.getArgs()[0].toString();
        int beginIdx = (Integer) args.getArgs()[1];
        // Not enough source to get a substring.
        if (beginIdx >= source.length())
            return "";

        if (len == 3) {
            int endIdx = (Integer) args.getArgs()[2];
            // Not enough source to work with, get what we can.
            if (endIdx > source.length())
                endIdx = source.length();
            return source.substring(beginIdx, endIdx);
        }
        String retStr = source.substring(beginIdx);
        return retStr;
    }

    private String deriveReplace(FunctionArgs funcArgs) {
        if (funcArgs.getArgs().length != 3)
            throw new IllegalStateException("replace must have 3 arguments.  Received: " + funcArgs.getArgs().length);

        String strToReplace = funcArgs.getArgs()[0].toString();
        String fromChars = funcArgs.getArgs()[1].toString();
        String toChars = funcArgs.getArgs()[2].toString();
        return strToReplace.replace(fromChars, toChars);
    }
}
