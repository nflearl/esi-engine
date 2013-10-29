package org.netkernelroc.esi.domain;

/**
 *
 */
public class Try implements ESITag {

    private final boolean isEnd;
    private final String startTag = "<esi:try>";
    private final String endTag = "</esi:try>";

    public Try(boolean end) {
        isEnd = end;
    }

    @Override
    public String render() {
        return (isEnd) ? endTag : startTag;
    }
}
