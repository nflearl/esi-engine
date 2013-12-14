package org.netkernelroc.esi.parsing;

import org.netkernelroc.esi.domain.*;

/**
 *
 */
public class ESITagFactory {

    public ESITagFactory() {
    }

    public ESITag createTag(Tag tagNode) {
System.out.println("EARLTAG: " + tagNode.getTagPayload());
        EsiTagType tagType = EsiTagType.valueOf(tagNode.getTagName());
        switch (tagType) {
        case ASSIGN:
            return new Assign(tagNode.getAttribute("name"), tagNode.getAttribute("value"));

        case ATTEMPT:
            return new Attempt();

        case CHOOSE:
            return new Choose(tagNode.isEndTag());

        case EVAL:
            return new Eval(tagNode.getAttribute("src"));

        case EXCEPT:
            return new Except();

        case INCLUDE:
            String src = tagNode.getAttribute("src");
            String onError = tagNode.getAttribute("onerror");
            return (onError == null) ? new Include(src) : new Include(src, onError);

        case OTHERWISE:
            return new Otherwise();

        case TRY:
            return new Try(tagNode.isEndTag());

        case WHEN:
            return new When();

        default:
            throw new IllegalStateException("Unexpected Tag Type " + tagType.name());
        }
    }
}
