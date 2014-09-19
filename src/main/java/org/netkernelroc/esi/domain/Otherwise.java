package org.netkernelroc.esi.domain;

/**
 *
 */
public class Otherwise extends StartOrEnd {

    public Otherwise(boolean end) {
        super(end);
    }

    @Override
    protected String getTagName() {
        return "otherwise";
    }

    @Override
    public boolean isChooseCase() {
        return true;
    }
}
