package org.netkernelroc.esi.expression.domain;

/*
 * A DTO for representing multi arg.
 */
public class FunctionArgs implements Comparable {

    private final Comparable[] args;
    private int freeIdx;

    public FunctionArgs(int numArgs) {
        args = new Comparable[numArgs];
    }

    public void addArg(Comparable comp) {
        args[freeIdx++] = comp;
    }

    public Comparable[] getArgs() {
        return args;
    }

    @Override
    public int compareTo(Object other) {
        // Kinda cheezy, but we really don't have a current need for it.
        throw new UnsupportedOperationException();
    }
}
