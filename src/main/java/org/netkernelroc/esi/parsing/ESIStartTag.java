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

    @Override
    public String getAttribute(String key) {
        int idx = tagPayload.indexOf(key);
        if (idx < 0)
            throw new IllegalStateException("Could not find key: " + key + " and don't know how we should handle it at this point");

        idx = tagPayload.indexOf("=", idx);
        if (idx < 0)
            throw new IllegalStateException("Could not find = and don't know how we should handle it at this point");

        idx += 2; // get past the = sign and the open "
        int endIdx = tagPayload.indexOf('"', idx);
        if (endIdx < 0)
            endIdx = tagPayload.length() - 1;
        return tagPayload.substring(idx, endIdx);
    }
}
