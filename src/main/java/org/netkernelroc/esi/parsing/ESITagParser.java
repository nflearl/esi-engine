package org.netkernelroc.esi.parsing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ESITagParser {

    public ESITagParser() {
    }

    public List<Tag> parse(String rawText) {

        List<Tag> tokenList = new LinkedList<Tag>();

        int curIdx = 0;
        int nextStartTag = nextStartTag(rawText, 0);
        int nextEndTag = nextEndTag(rawText, 0);
        while (curIdx < rawText.length()) {
            // No more tags
            if (nextEndTag < 0 && nextStartTag < 0) {
                tokenList.add(new Literal(rawText.substring(curIdx, rawText.length())));
                break;
            }

            int closingIdx = -1;

            // Start tag next.  <esi: ... >
            if (nextEndTag < 0 || nextStartTag < nextEndTag) {
                if (nextStartTag > curIdx)
                    tokenList.add(new Literal(rawText.substring(curIdx, nextStartTag)));
                // Find the end of the tag.
                closingIdx = findClosingTagChar(rawText, nextStartTag);
                if (closingIdx < 0)
                    throw new IllegalStateException("No > to complete ESI tag.");
                tokenList.add(new ESIStartTag(rawText.substring(nextStartTag, closingIdx)));
                nextStartTag = nextStartTag(rawText, closingIdx);
            } else {    // End tag next  </esi: ... >
                if (nextEndTag > curIdx)
                    tokenList.add(new Literal(rawText.substring(curIdx, nextEndTag)));
                // Find the end of the tag.
                closingIdx = findClosingTagChar(rawText, nextEndTag);
                if (closingIdx < 0)
                    throw new IllegalStateException("No > to complete ESI tag.");
                tokenList.add(new ESIEndTag(rawText.substring(nextEndTag, closingIdx)));
                nextEndTag = nextEndTag(rawText, closingIdx);
            }

            curIdx = closingIdx;
        }

        return tokenList;
    }

    private int findClosingTagChar(String rawText, int curIdx) {
        int closingIdx = rawText.indexOf(">", curIdx);
        if (closingIdx < 0)
            throw new IllegalStateException("Very bad source, no completion of a given tag.");
        closingIdx++;
        return closingIdx;
    }

    private int nextStartTag(String rawText, int fromIdx) {
        return rawText.indexOf("<esi:", fromIdx);
    }

    private int nextEndTag(String rawText, int fromIdx) {
        return rawText.indexOf("</esi:", fromIdx);
    }
}
