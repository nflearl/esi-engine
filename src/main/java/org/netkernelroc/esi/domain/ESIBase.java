package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class ESIBase implements ESITag {

    private List<ESITag> children = Collections.emptyList();

    protected ESIBase() {
    }

    protected List<ESITag> getChildren() {
        return children;
    }

    public void setChildren(List<ESITag> children) {
        this.children = children;
    }

    protected void noKidPolicy() {
        if (!getChildren().isEmpty())
            throw new IllegalStateException("This tag type should not have any embedded tags.");
    }

    @Override
    public boolean isChooseCase(ESIContext esiContext) {
        return false;
    }
}
