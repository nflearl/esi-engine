package org.netkernelroc.esi.rendering;

/**
 *
 */
public interface ESIContext {

    public static final String SCRATCH_SPACE_PATH = "/ESI_CONTEXT";

    void assignVariable(String variableName, String value);
    String retrieveVariable(String variableName);

    String resolveInclude(String relativePath);
    String lookupHttpParam(String key);

    String getPath();
    String getHost();

    String resolveExpression(String expr);
    boolean invokeExpression(String expr, String matchName);
    void saveMatchValues(String[] values);
}
