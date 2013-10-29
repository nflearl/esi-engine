package org.netkernelroc.esi.parsing;

import org.htmlparser.Node;
import org.netkernelroc.esi.domain.ESITag;

/**
 *
 */
public class ESITagNodeHolder implements ESITag {

    private final Node esiTag;

    public ESITagNodeHolder(Node esiTag) {
        this.esiTag = esiTag;
    }

    @Override
    public String render() {
        throw new UnsupportedOperationException();
    }
}
