package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class ESIRoot extends ESIBase {

    public ESIRoot() {
    }

    @Override
    public void render(ESIContext esiContext, StringBuilder results) {
        for (ESITag tag : getChildren())
            tag.render(esiContext, results);
    }

    @Override
    public String renderOrig() {
        return "";
    }

    @Override
    public String renderOrigEnd() {
        return "";
    }
}
