package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;

import java.util.List;

/**
 *
 */
public interface ESITag {

    String ESI_CHECK_RESULT = "ESICHECK";

    // Production version, the other render methods are for testing the engine.
    void render(ESIContext esiContext, StringBuilder result);

    String renderOrig();

    String renderOrigEnd(); // Render the end tag version

    void setChildren(List<ESITag> children);

    boolean pickMe();
}
