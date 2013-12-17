package org.netkernelroc.esi.domain;

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

    public String render() {
        return "<EARL:include" +
                ((onError.isEmpty()) ? "" : " onerror=\"" + onError + "\"") +
                " src=\"" + srcUrl + "\"/>";
    }

    @Override
    public String renderEnd() {
        throw new UnsupportedOperationException();
    }
}
