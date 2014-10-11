package org.netkernelroc.esi.expression;

import org.netkernel.layer0.nkf.INKFRequest;
import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;

public class ESIExpressionRuntime extends StandardAccessorImpl {

    public void onSource(INKFRequestContext context) throws Exception
    {
        String expression = context.getThisRequest().getArgumentValue("expression");
        INKFRequest antlrReq = context.createRequest("active:antlr");
//        antlrReq.addArgument("operator", "file:/grammar/esi_exp.g");
        antlrReq.addArgument("operator", "file:/work5/esi/github/esi-engine/src/main/resources/grammar/esi_exp.g");
        antlrReq.addArgumentByValue("operand", expression);
        antlrReq.addArgumentByValue("startrule", "eval");

        try {
            Object result = context.issueRequest(antlrReq);
            context.createResponseFrom(result);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
