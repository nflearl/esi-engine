package org.netkernelroc.esi.domain;

/**
 *
 */
public class Include implements ESITag {

    private final String srcUrl;

    public Include(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String render() {
        return "<EARL:include src=\"" + srcUrl + "\"/>";
    }
}
