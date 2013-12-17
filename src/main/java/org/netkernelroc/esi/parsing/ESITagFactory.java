package org.netkernelroc.esi.parsing;

import org.netkernelroc.esi.domain.*;

/**
 *
 */
public class ESITagFactory {

    public ESITagFactory() {
    }

    public ESITag createTag(Tag tagNode) {
        EsiTagType tagType = EsiTagType.valueOf(tagNode.getTagName());
        switch (tagType) {
        case ASSIGN:
            return new Assign(tagNode.getAttribute("name"), tagNode.getAttribute("value"));

        case ATTEMPT:
            return new Attempt(tagNode.isEndTag());

        case CHOOSE:
            return new Choose(tagNode.isEndTag());

        case EVAL:
            String onError = tagNode.getAttribute("onerror");
            String dca = tagNode.getAttribute("dca");
            String src = tagNode.getAttribute("src");
            return (onError != null) ? new Eval(src, dca, onError) : new Eval(src);

        case EXCEPT:
            return new Except(tagNode.isEndTag());

        case INCLUDE:
            src = tagNode.getAttribute("src");
            onError = tagNode.getAttribute("onerror");
            return (onError == null) ? new Include(src) : new Include(src, onError);

        case OTHERWISE:
            return new Otherwise(tagNode.isEndTag());

        case TRY:
            return new Try(tagNode.isEndTag());

        case WHEN:
            String matchName = tagNode.getAttribute("matchname");
            String test = tagNode.getAttribute("test");
            return (matchName == null) ? new When(test) : new When(test, matchName);

        default:
            throw new IllegalStateException("Unexpected Tag Type " + tagType.name());
        }
    }
}
