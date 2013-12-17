package org.netkernelroc.esi.domain;

/**
 *
 */
public class Try extends StartOrEnd {

    public Try(boolean end) {
        super(end);
    }

    @Override
    protected String getTagName() {
        return "try";
    }
}
