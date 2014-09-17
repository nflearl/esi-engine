package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class ESILiteral implements ESITag {

    private String literalPayload;

    public ESILiteral(String payload) {
        literalPayload = payload;
    }

    @Override
    public String render(ESIContext esiContext) {
        return literalPayload;
    }

    @Override
    public String renderOrig() {
        return literalPayload;
    }

    @Override
    public String renderOrigEnd() {
        return literalPayload;
    }
}
