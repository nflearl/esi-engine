package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.*;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;
import org.netkernelroc.esi.rendering.ESIContext;
import org.netkernelroc.esi.rendering.RenderEngine;


/**
 *
 */
public class RenderResource extends StandardAccessorImpl {

    public static final String SCRATCH_SPACE = "scratch:esi/assign/";

    public RenderResource() {
        // Allows reentrancy for esi including other esi which includes ...
        this.declareThreadSafe();
    }

    public void onSource(INKFRequestContext context) throws Exception
    {
        String payload = context.getThisRequest().getArgumentValue("payload");
        String host = context.getThisRequest().getArgumentValue("host");
        String port = context.getThisRequest().getArgumentValue("port");
        String path = context.getThisRequest().getArgumentValue("path");

        RenderEngine renderEngine = new RenderEngine(payload);
        ESIContext esiContext = buildContext(context, host, port, path);
        context.createResponseFrom(renderEngine.renderedResults(esiContext).toString().trim());
    }

    private ESIContext buildContext(final INKFRequestContext context, final String host, final String port,
                                    final String path) {
        return new ESIContext() {
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
        };
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
