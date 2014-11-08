package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

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

    @Override
    public void render(ESIContext esiContext, StringBuilder result) {
        for (ESITag kid : getChildren()) {
            if (kid.isChooseCase(esiContext)) {
                kid.render(esiContext, result);
                return;
            }
        }
    }
}
