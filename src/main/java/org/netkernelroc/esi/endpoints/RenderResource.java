package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.*;
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
        String path = context.getThisRequest().getArgumentValue("path");

        RenderEngine renderEngine = new RenderEngine(payload);
        ESIContext esiContext = new ESIContextImpl(context, host, port, path);
        context.createResponseFrom(renderEngine.renderedResults(esiContext).toString().trim());
    }
}
