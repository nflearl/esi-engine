package org.netkernelroc.esi.expression;

import org.netkernel.layer0.nkf.INKFRequest;
import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.layer0.representation.IHDSNode;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;
import org.netkernelroc.esi.endpoints.ESITestContextImpl;
import org.netkernelroc.esi.expression.domain.ExpressionBuilder;
import org.netkernelroc.esi.rendering.ESIContext;

public class ESIExpressionRuntime extends StandardAccessorImpl {

    public void onSource(INKFRequestContext context) throws Exception
    {
        String expression = context.getThisRequest().getArgumentValue("expression");
        INKFRequest antlrReq = context.createRequest("active:antlr");
        antlrReq.addArgument("operator", "res:/grammar/esi_exp.g");
        antlrReq.addArgumentByValue("operand", expression);
        antlrReq.addArgumentByValue("startrule", "eval");

        try {
            ESIContext esiContext = new ESITestContextImpl(context);
            IHDSNode result = (IHDSNode) context.issueRequest(antlrReq);
            boolean computedResult = new ExpressionBuilder(esiContext).build(result).evaluate(result.getChildren());
            context.createResponseFrom(computedResult);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
