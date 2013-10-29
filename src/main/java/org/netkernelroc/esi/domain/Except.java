package org.netkernelroc.esi.domain;

/**
 *
 */
public class Except implements ESITag {

    @Override
    public String render() {
        throw new UnsupportedOperationException();
    }
}
