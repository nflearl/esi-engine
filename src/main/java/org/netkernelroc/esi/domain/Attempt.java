package org.netkernelroc.esi.domain;

/**
 *
 */
public class Attempt extends StartOrEnd {

    public Attempt(boolean end) {
        super(end);
    }

    @Override
    protected String getTagName() {
        return "attempt";
    }
}
