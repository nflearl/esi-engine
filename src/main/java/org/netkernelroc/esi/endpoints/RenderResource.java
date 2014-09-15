package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.INKFLocale;
import org.netkernel.layer0.nkf.INKFRequest;
import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.layer0.nkf.NKFException;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;
import org.netkernelroc.esi.rendering.ESIContext;
import org.netkernelroc.esi.rendering.RenderEngine;


/**
 *
 */
public class RenderResource extends StandardAccessorImpl {

    public RenderResource() {
        // Allows reentrancy for esi including other esi which includes ...
        this.declareThreadSafe();
    }

    public void onSource(INKFRequestContext context) throws Exception
    {
        String payload = context.getThisRequest().getArgumentValue("payload");
        String host = context.getThisRequest().getArgumentValue("host");
        String port = context.getThisRequest().getArgumentValue("port");

        RenderEngine renderEngine = new RenderEngine(payload);
        ESIContext esiContext = buildContext(context, host, port);
        context.createResponseFrom(renderEngine.renderedResults(esiContext).toString().trim());
    }

    private ESIContext buildContext(final INKFRequestContext context, final String host, final String port) {
        return new ESIContext() {
            @Override
            public void assignVariable(String variableName, String value) {

            }

            @Override
            public String retrieveVariable(String variableName) {
                return "";
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
                    return result;
                } catch (NKFException nkfe) {
                    throw new RuntimeException(nkfe);
                }
            }
        };
    }
}
