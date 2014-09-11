package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.parsing.VariableSubstituter;
import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class Assign implements ESITag {
    private final String name;
    private final String value;

    public Assign(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String renderOrig() {
        return "<" + ESI_CHECK_RESULT + ":assign name=\"" + name + "\" value=\"" + value +"\"/>";
    }

    @Override
    public String renderOrigEnd() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render(ESIContext esiContext) {
        String substitutedValue =  new VariableSubstituter(esiContext).substitute(value);
        esiContext.assignVariable(name, substitutedValue);
        return "";
    }
}
