package org.netkernelroc.esi.parsing;

import org.netkernelroc.esi.rendering.ESIContext;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class VariableSubstituter {

    private final ESIContext context;
    private Map<String, ReservedHttpVariables> reservedVariable = new HashMap<String, ReservedHttpVariables>(89);

    public VariableSubstituter(ESIContext context) {
        this.context = context;
        for (ReservedHttpVariables rhv : ReservedHttpVariables.values())
            reservedVariable.put(rhv.name(), rhv);
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
                int jdx = split.indexOf('{');
                int end = (jdx == -1) ? idx : jdx;
                String varName = split.substring(1, end);
                if (reservedVariable.containsKey(varName)) {
                    String lookupKey = (jdx == -1) ? "" : extractInnerParameter(split.substring(end + 1));
                    sb.append(reservedVariable.get(varName).render(lookupKey, context));
                } else {
                    sb.append(context.retrieveVariable(varName));
                }
                sb.append(split.substring(idx + 1));
            } else {
                sb.append(split);
            }
        }

        return sb.toString();
    }

    public String resolve(String varName, String lookupKey) {
        if (reservedVariable.containsKey(varName)) {
            return reservedVariable.get(varName).render(lookupKey, context);
        } else {
            String retStr = context.retrieveVariable(varName);
            // Hack for the matcher variables.
            if (retStr.isEmpty())
                return context.retrieveVariable(varName + '{' + lookupKey + '}');

            return retStr;
        }
    }

    private String extractInnerParameter(String param) {
        String trimmedParam = param.trim();
        if (trimmedParam.isEmpty())
            return trimmedParam;

        String[] splitSrc = trimmedParam.split("'");
        if (splitSrc.length >= 2)
            return splitSrc[1];
        return trimmedParam;
    }

    public boolean isReserved(String varName) {
        return reservedVariable.containsKey(varName);
    }

    public String deriveNoKey(String varName) {
        return reservedVariable.get(varName).render("", context);
    }
}
