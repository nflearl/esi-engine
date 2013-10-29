package org.netkernelroc.esi.domain;

/**
 *
 */
public class Eval implements ESITag {

    private final String srcUrl;

    public Eval(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    @Override
    public String render() {
        return "<EARL:eval src=\"" + srcUrl + "\"/>";
    }
}
