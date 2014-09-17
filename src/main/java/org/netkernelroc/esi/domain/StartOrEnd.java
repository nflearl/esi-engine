package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public abstract class StartOrEnd extends ESIBase {
    private final String isEndStr;

    public StartOrEnd(boolean end) {
        isEndStr = end ? "/" : "";
    }

    protected abstract String getTagName();

    @Override
    public String renderOrig() {
        return "<" + isEndStr + ESI_CHECK_RESULT + ":" + getTagName() + ">";
    }

    @Override
    public String renderOrigEnd() {
        return "</" + ESI_CHECK_RESULT+ ":" + getTagName() + ">";
    }

    @Override
    public void render(ESIContext esiContext, StringBuilder result) {

        throw new UnsupportedOperationException();
    }
}
