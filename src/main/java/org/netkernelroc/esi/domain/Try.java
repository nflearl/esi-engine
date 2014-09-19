package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

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

    @Override
    public void render(ESIContext esiContext, StringBuilder result) {
        try {
            for (ESITag kid : getChildren()) {
                // Attempt the first attempt statement
                if (kid instanceof Attempt) {
                    kid.render(esiContext, result);
                    break;
                }
            }
        } catch (Exception ex) {
            for (ESITag kid : getChildren()) {
                // Attempt the first attempt statement
                if (kid instanceof Except) {
                    kid.render(esiContext, result);
                    break;
                }
            }
        }
    }
}
