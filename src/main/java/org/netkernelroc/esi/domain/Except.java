package org.netkernelroc.esi.domain;

/**
 *
 */
public class Except extends StartOrEnd {

    public Except(boolean end) {
        super(end);
    }

    @Override
    protected String getTagName() {
        return "except";
    }
}
