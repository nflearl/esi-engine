package org.netkernelroc.esi.endpoints;

import org.netkernel.layer0.nkf.INKFRequestContext;

public class ESITestContextImpl extends ESIContextImpl {

    public ESITestContextImpl(INKFRequestContext context, String path) {
        // TODO - nulls are bad here, figure out something else.
        super(context, null, null, path);
    }

    @Override
    public String lookupHttpParam(String key) {
        return retrieveVariable("QUERY_STRING/" + key);
    }
}
