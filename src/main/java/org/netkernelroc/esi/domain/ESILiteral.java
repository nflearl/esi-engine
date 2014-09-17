package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class ESILiteral extends ESIBase {

    private String literalPayload;

    public ESILiteral(String payload) {
        literalPayload = payload;
    }

    @Override
    public void render(ESIContext esiContext, StringBuilder result) {
        noKidPolicy();
        result.append(literalPayload);
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
