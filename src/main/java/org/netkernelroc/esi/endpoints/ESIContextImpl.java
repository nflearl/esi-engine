package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.*;
import org.netkernelroc.esi.rendering.ESIContext;

public class ESIContextImpl implements ESIContext {
    public static final String SCRATCH_SPACE = "scratch:esi/assign/";

    private final INKFRequestContext context;
    private final String host;
    private final String port;
    private final String path;

    public ESIContextImpl(INKFRequestContext context, String host, String port, String path) {
        this.context = context;
        this.host = host;
        this.port = port;
        this.path = path;
    }

    @Override
    public void assignVariable(String variableName, String value) {
        // Only store "real" values
        if (value == null || value.isEmpty())
            return;
        value = stripSingleQuotes(value);
        if (value.isEmpty())
            return;

        try {
            INKFRequest req = context.createRequest(SCRATCH_SPACE + variableName);
            req.setVerb(INKFRequestReadOnly.VERB_SINK);
            req.addPrimaryArgument(value);
            context.issueRequest(req);
        } catch (NKFException nkfe) {
            throw new RuntimeException(nkfe);
        }
    }

    @Override
    public String retrieveVariable(String variableName) {
        INKFRequest req = null;
        try {
            req = context.createRequest(SCRATCH_SPACE + variableName);
            req.setVerb(INKFRequestReadOnly.VERB_EXISTS);
            boolean exists = (Boolean) context.issueRequest(req);
            if (!exists)
                return "";

            req.setVerb(INKFRequestReadOnly.VERB_SOURCE);
            Object cachedValue = context.issueRequest(req);
            return (cachedValue == null) ? "" : cachedValue.toString();

        } catch (NKFException nkfe) {
            throw new RuntimeException(nkfe);
        }
    }

    @Override
    public String resolveInclude(String relativePath) {
        String url = "http://" + host + ":" + port + relativePath;
        context.logRaw(INKFLocale.LEVEL_INFO, "resolveInclude: " + url);
        try {
            INKFRequest req = context.createRequest("active:httpGet");
            req.addArgument("url", url);
            req.setRepresentationClass(String.class);
            String html = context.issueRequest(req).toString();
            return html;
        } catch (NKFException nkfe) {
            throw new RuntimeException(nkfe);
        }
    }

    @Override
    public String lookupHttpParam(String key) {
        try {
            String result = context.source("httpRequest:/param/" + key, String.class);
            return (result == null) ? "" : result;
        } catch (NKFException nkfe) {
            throw new RuntimeException(nkfe);
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    private String stripSingleQuotes(String src) {
        final String EMPTY_QUOTES = "''";
        final char EMPTY_QUOTE = '\'';

        if (EMPTY_QUOTES.equals(src))
            return "";

        if (src.isEmpty())
            return src;

        String retStr = src;
        if (src.charAt(0) == EMPTY_QUOTE)
            retStr = src.substring(1);

        if (retStr.isEmpty())
            return src;

        if (retStr.charAt(retStr.length() - 1) == EMPTY_QUOTE)
            retStr = retStr.substring(0, retStr.length() -1);

        return retStr;
    }
}
