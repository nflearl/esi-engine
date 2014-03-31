package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;


import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the mapping from a host name to where the physical html files are located
 * (whether on the file system or perhaps on a remote origin server).
 */
public class OriginMapperResources extends StandardAccessorImpl {

    private static Map<String, String> hostNameMapping = new HashMap<String, String>(89);

    // As an example.  One can have many different hosts mapping to different source locations.
    static {
        hostNameMapping.put("localhost", "file:///C:/origin/localhost/");
    }

    public void onSource(INKFRequestContext context) throws Exception
    {
        String host = context.getThisRequest().getArgumentValue("host");
        // Happy case
        if (hostNameMapping.containsKey(host))
            context.createResponseFrom(hostNameMapping.get(host));
        else
            throw new UnsupportedOperationException("Oh no!, no host found");
    }
}
