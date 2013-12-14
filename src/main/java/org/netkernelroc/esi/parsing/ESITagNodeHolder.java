package org.netkernelroc.esi.parsing;

import org.netkernelroc.esi.domain.ESITag;

/**
 *
 */
public class ESITagNodeHolder implements ESITag {

    private final Tag esiTag;

    public ESITagNodeHolder(Tag esiTag) {
        this.esiTag = esiTag;
    }

    @Override
    public String render() {
        throw new UnsupportedOperationException();
    }
}
