package org.netkernelroc.esi.domain;

/**
 *
 */
public class Choose extends StartOrEnd {

    public Choose(boolean end) {
        super(end);
    }

    @Override
    protected String getTagName() {
        return "choose";
    }
}
