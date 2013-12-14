package org.netkernelroc.esi.parsing;

/**
 *
 */
public class Literal extends Tag {

    public Literal(String tagPayload) {
        super(tagPayload);
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public boolean isEndTag() {
        return false;
    }
}
