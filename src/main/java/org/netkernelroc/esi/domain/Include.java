package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.parsing.VariableSubstituter;
import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class Include implements ESITag {

    private final String srcUrl;
    private final String onError;   // Optional

    public Include(String srcUrl) {
        this.srcUrl = srcUrl;
        this.onError = "";
    }

    public Include(String srcUrl, String onError) {
        this.srcUrl = srcUrl;
        this.onError = onError;
    }

    public String renderOrig() {
        return "<" + ESI_CHECK_RESULT + ":include" +
                ((onError.isEmpty()) ? "" : " onerror=\"" + onError + "\"") +
                " src=\"" + srcUrl + "\"/>";
    }

    @Override
    public String renderOrigEnd() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String render(ESIContext esiContext) {
        String convertedUrl = new VariableSubstituter(esiContext).substitute(srcUrl);
        return esiContext.resolveInclude(convertedUrl);
    }
}
