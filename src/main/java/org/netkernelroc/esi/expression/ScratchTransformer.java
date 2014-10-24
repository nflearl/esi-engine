package org.netkernelroc.esi.expression;

import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.layer0.util.Utils;
import org.netkernel.module.standard.endpoint.TransparentOverlayImpl;
import org.netkernelroc.esi.endpoints.ESITestContextImpl;
import org.netkernelroc.esi.rendering.ESIContext;

import java.util.Set;

/*
 * Helper class for testing.
 */
public class ScratchTransformer extends TransparentOverlayImpl {

    public static final String SCRATCH_PREFIX = "XXX-Scratch:";

    public ScratchTransformer() {
        this.declareThreadSafe();
    }

    @Override
    public void onRequest(String s, INKFRequestContext inkfRequestContext) throws Exception {
        // Initialize all the test parameters into the scratch space
        Set<String> keySet = inkfRequestContext.getThisRequest().getHeaderKeys();
        for (String key : keySet) {
            if (key.startsWith(SCRATCH_PREFIX)) {
                String scratchKey = "scratch:" + key.substring(SCRATCH_PREFIX.length());
                Object scratchValue = inkfRequestContext.getThisRequest().getHeaderValue(key);
                inkfRequestContext.sink(scratchKey, scratchValue);
            }
        }

        // Initialize the context.
        inkfRequestContext.sink("scratch:" + ESIContext.SCRATCH_SPACE_PATH, new ESITestContextImpl(inkfRequestContext));

        Utils.delegateRequestInto(getDelegateSpace(), inkfRequestContext);
    }
}
