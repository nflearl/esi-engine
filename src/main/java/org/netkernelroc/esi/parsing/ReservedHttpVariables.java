package org.netkernelroc.esi.parsing;

import org.netkernelroc.esi.rendering.ESIContext;

/**
 *
 */
public enum ReservedHttpVariables {

    // ESI Spec
    HTTP_ACCEPT_LANGUAGE {
        @Override
        public String render(String argument, ESIContext context) {
            throw new UnsupportedOperationException();
        }
    }, HTTP_COOKIE {
        @Override
        public String render(String argument, ESIContext context) {
            throw new UnsupportedOperationException();
        }
    }, HTTP_HOST {
        @Override
        public String render(String argument, ESIContext context) {
            return context.getHost();
        }
    }, HTTP_REFERER {
        @Override
        public String render(String argument, ESIContext context) {
            throw new UnsupportedOperationException();
        }
    }, HTTP_USER_AGENT {
        @Override
        public String render(String argument, ESIContext context) {
            throw new UnsupportedOperationException();
        }
    }, QUERY_STRING {
        @Override
        public String render(String argument, ESIContext context) {
            return context.lookupHttpParam(argument);
        }
    // Akamai Extensions
    }, REQUEST_PATH {
        @Override
        public String render(String argument, ESIContext context) {
            return context.getPath();
        }
    }
    ;

    public abstract String render(String argument, ESIContext context);
}
