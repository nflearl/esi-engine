package org.netkernelroc.esi.domain;

/**
 *
 */
public interface ESITag {

    String ESI_CHECK_RESULT = "ESICHECK";

    String render();

    String renderEnd(); // Render the end tag version
}
