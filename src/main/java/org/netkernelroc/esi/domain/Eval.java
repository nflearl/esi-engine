package org.netkernelroc.esi.domain;

/**
 *
 */
public class Eval implements ESITag {

    private final String srcUrl;
    private final String dca;
    private final String onerror;

    public Eval(String srcUrl) {
        this.srcUrl = srcUrl;
        dca = "";
        onerror = "";
    }

    public Eval(String srcUrl, String dca, String onerror) {
        this.srcUrl = srcUrl;
        this.dca = dca;
        this.onerror = onerror;
    }

    @Override
    public String render() {
        return "<" + ESI_CHECK_RESULT + ":eval src=\"" + srcUrl +
                ((dca.isEmpty()) ? "" : "\" dca=\"" + dca) +
                ((onerror.isEmpty()) ? "" : "\" onerror=\"" + onerror) +
                "\"/>";
    }

    @Override
    public String renderEnd() {
        throw new UnsupportedOperationException();
    }
}
