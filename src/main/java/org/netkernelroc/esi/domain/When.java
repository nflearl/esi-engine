package org.netkernelroc.esi.domain;

/**
 *
 */
public class When implements ESITag {

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
    public String render() {
        return "<EARL:when test=\"" + testExpression +
                ((matchName.isEmpty()) ? "" : "\" matchname=\"" + matchName) +
                "\">";
    }

    @Override
    public String renderEnd() {
        return "</EARL:when>";
    }
}
