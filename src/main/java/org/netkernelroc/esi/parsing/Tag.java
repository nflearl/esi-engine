package org.netkernelroc.esi.parsing;

/**
 *
 */
public abstract class Tag {

    protected final String tagPayload;

    protected Tag(String tagPayload) {
        this.tagPayload = tagPayload;
    }

    public String getTagPayload() {
        return tagPayload;
    }

    public abstract boolean isLiteral();

    public abstract boolean isComplete();   // i.e. ends with />

    public abstract boolean isEndTag();     // i.e. </esi:blah>

    public String getTagName() {
        throw new UnsupportedOperationException();
    }

    public String getAttribute(String key) {
        throw new UnsupportedOperationException();
    }
}
