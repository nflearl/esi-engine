package org.netkernelroc.esi.parsing;

/**
 *
 */
public class ESIStartTag extends Tag {

    public ESIStartTag(String tagPayload) {
        super(tagPayload);
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return tagPayload.endsWith("/>");
    }

    @Override
    public boolean isEndTag() {
        return false;
    }

    @Override
    public String getTagName() {
        int tagNameEndIdx = tagPayload.indexOf(' ');
        if (tagNameEndIdx == -1)    // No space after the name, must be the only thing in the tag.
            tagNameEndIdx = tagPayload.length() - 1;
        String tagName = tagPayload.substring(5, tagNameEndIdx);
        return tagName.toUpperCase();
    }
}
