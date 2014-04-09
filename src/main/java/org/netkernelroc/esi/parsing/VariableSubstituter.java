package org.netkernelroc.esi.parsing;

import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class VariableSubstituter {

    private final ESIContext context;

    public VariableSubstituter(ESIContext context) {
        this.context = context;
    }

    public String substitute(String src) {
        if (src == null || src.isEmpty())
            return "";

        String[] splitSrc = src.split("\\$");
        if (splitSrc.length == 1)
            return splitSrc[0];

        StringBuilder sb = new StringBuilder();
        for (String split : splitSrc) {
            if (split.startsWith("(")) {
                int idx = split.indexOf(')');
                String varName = split.substring(1, idx);
                sb.append(context.retrieveVariable(varName));
                sb.append(split.substring(idx + 1));
            } else {
                sb.append(split);
            }
        }

        return sb.toString();
    }
}
