package org.netkernelroc.esi.parsing;

/**
 *
 */
public class ESIEndTag extends BaseTag {

    public ESIEndTag(String tagPayload) {
        super(tagPayload);
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isEndTag() {
        return true;
    }
}
