package org.netkernelroc.esi.domain;

/**
 *
 */
public interface ESITag {

    String ESI_CHECK_RESULT = "ESICHECK";

    String renderOrig();

    String renderOrigEnd(); // Render the end tag version
}
