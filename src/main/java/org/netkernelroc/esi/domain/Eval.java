package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

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
    public String renderOrig() {
        return "<" + ESI_CHECK_RESULT + ":eval src=\"" + srcUrl +
                ((dca.isEmpty()) ? "" : "\" dca=\"" + dca) +
                ((onerror.isEmpty()) ? "" : "\" onerror=\"" + onerror) +
                "\"/>";
    }

    @Override
    public String renderOrigEnd() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render(ESIContext esiContext) {
        // TODO - placeholder for now
        if (this != null)
            return "";
        throw new UnsupportedOperationException();
    }
}
