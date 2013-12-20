package org.netkernelroc.esi.domain;

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
}
