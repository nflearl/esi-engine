package org.netkernelroc.esi.domain;

import org.netkernelroc.esi.rendering.ESIContext;
/**
 *
 */
public interface ESITag {

    String ESI_CHECK_RESULT = "ESICHECK";

    String render(ESIContext esiContext);    // Production version, the other render methods are for testing the engine.

    String renderOrig();

    String renderOrigEnd(); // Render the end tag version
}
