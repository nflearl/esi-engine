package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

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
    public boolean isChooseCase(ESIContext esiContext) {
        return true;
    }
}
