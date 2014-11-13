package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.*;
import org.netkernelroc.esi.parsing.VariableSubstituter;
import org.netkernelroc.esi.rendering.ESIContext;

public class ESIContextImpl implements ESIContext {
    public static final String SCRATCH_SPACE = "scratch:esi/assign/";
    public static final String SCRATCH_ESI_SAVED_MATCH_VALS = "scratch:esi/MATCHVALUES";

    private final VariableSubstituter vs = new VariableSubstituter(this);

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
        // TODO - more Tech Debt, arising from the fact that VariableSubstituter was built for raw text, but
        // I also need to convert those variables in expressions.  In need of refactoring/simplifications.
        if (vs.isReserved(variableName))
            return vs.deriveNoKey(variableName);

        try {
            INKFRequest req = context.createRequest(SCRATCH_SPACE + variableName);
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
            @SuppressWarnings("UnnecessaryLocalVariable")
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

    @Override
    public String getHost() {
        return host;
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

    @Override
    public boolean invokeExpression(String expr, String matchName) {
        try {
            context.sink("scratch:" + ESIContext.SCRATCH_SPACE_PATH, this);
            INKFRequest req = context.createRequest("active:ESIExpressionRuntime");
            req.setVerb(INKFRequestReadOnly.VERB_SOURCE);
            req.addArgument("expression", expr);
            req.setRepresentationClass(Boolean.class);
            @SuppressWarnings("UnnecessaryLocalVariable")
            Boolean result = (Boolean) context.issueRequest(req);
            if (result)
                populateMatchName(matchName);
            return result;
        } catch (NKFException nkfe) {
            throw new RuntimeException(nkfe);
        }
    }

    private void populateMatchName(String matchName) {
        try {
            // No match values saved (probably wasn't a match expression)
            if (!context.exists(SCRATCH_ESI_SAVED_MATCH_VALS))
                return;

            String[] values = context.source(SCRATCH_ESI_SAVED_MATCH_VALS, String[].class);
            for (int idx = 0; idx < values.length; idx++) {
                assignVariable(matchName + "{" + idx + "}", values[idx]);
            }
        } catch (NKFException nkfe) {
            throw new RuntimeException(nkfe);
        }
    }

    @Override
    public void saveMatchValues(String[] values) {
        try {
            context.sink(SCRATCH_ESI_SAVED_MATCH_VALS, values);
        } catch (NKFException nkfe) {
            throw new RuntimeException(nkfe);
        }
    }
}
