package org.netkernelroc.esi.rendering;

/**
 *
 */
public interface ESIContext {

    void assignVariable(String variableName, String value);
    String retrieveVariable(String variableName);

    String resolveInclude(String relativePath);
    String lookupHttpParam(String key);
}
