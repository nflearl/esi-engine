package org.netkernelroc.esi.domain;

/**
 *
 */
public abstract class StartOrEnd implements ESITag {
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
}
