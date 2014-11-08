package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.endpoints.ESITestContextImpl;
import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public class When extends ESIBase {

    private final String testExpression;
    private final String matchName;

    public When(String testExpression) {
        this.testExpression = testExpression;
        matchName = "";
    }

    public When(String testExpression, String matchName) {
        this.testExpression = testExpression;
        this.matchName = matchName;
    }

    @Override
    public String renderOrig() {
        return "<" + ESI_CHECK_RESULT + ":when test=\"" + testExpression +
                ((matchName.isEmpty()) ? "" : "\" matchname=\"" + matchName) +
                "\">";
    }

    @Override
    public String renderOrigEnd() {
        return "</" + ESI_CHECK_RESULT + ":when>";
    }

    @Override
    public void render(ESIContext esiContext, StringBuilder result) {
        for (ESITag kid : getChildren())
            kid.render(esiContext, result);
    }

    @Override
    public boolean isChooseCase(ESIContext esiContext) {

        String matchNameVar = (matchName == null || matchName.isEmpty()) ? "MATCHES" : matchName;
        return esiContext.invokeExpression(testExpression, matchNameVar);
    }
}
