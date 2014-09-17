package org.netkernelroc.esi.parsing;

import org.netkernel.layer0.representation.IHDSNode;
import org.netkernel.layer0.representation.impl.HDSBuilder;
import org.netkernelroc.esi.domain.ESITag;

/**
 *
 */
public class PageHolder {

    private enum TagType {HTML, ESI}

    private HDSBuilder hb = new HDSBuilder();

    public PageHolder() {
    }

    public void addCompleteEsiTag(ESITag tag) {
        hb.addNode(TagType.ESI.name(), tag);
    }

    public void addEsiTagStart(ESITag tag) {
        hb.pushNode(TagType.ESI.name(), tag);
    }

    public void esiTagEnd() {
        hb.popNode();
    }

    public IHDSNode getRoot() {
        return hb.getRoot();
    }
}
