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

    public String render() {
        return "<" + ESI_CHECK_RESULT + ":assign name=\"" + name + "\" value=\"" + value +"\"/>";
    }

    @Override
    public String renderEnd() {
        throw new UnsupportedOperationException();
    }
}
