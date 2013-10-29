package org.netkernelroc.esi.domain;

/**
 *
 */
public class Choose implements ESITag {

    private final boolean isEnd;
    private final String startTag = "<esi:choose>";
    private final String endTag = "</esi:choose>";


    public Choose(boolean end) {
        isEnd = end;
    }

    @Override
    public String render() {
        return (isEnd) ? endTag : startTag;
    }
}
