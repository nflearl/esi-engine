package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.parsing.VariableSubstituter;
import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class Assign extends ESIBase {
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
    public void render(ESIContext esiContext, StringBuilder results) {
        String substitutedValue = esiContext.resolveExpression(value);
        esiContext.assignVariable(name, substitutedValue);
    }
}
