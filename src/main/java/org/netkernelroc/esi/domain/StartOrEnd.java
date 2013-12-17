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
    public String render() {
        return "<EARL" + isEndStr + ":" + getTagName() + ">";
    }

    @Override
    public String renderEnd() {
        return "</EARL:" + getTagName() + ">";
    }
}
