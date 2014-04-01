package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;


/**
 *
 */
public class RenderResource extends StandardAccessorImpl {

    public void onSource(INKFRequestContext context) throws Exception
    {
        String payload = context.getThisRequest().getArgumentValue("payload");

        int idx = 57;
        idx++;

        context.createResponseFrom(payload);
    }
}
