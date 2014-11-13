package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;

public interface ESIExpression {

    Comparable evaluate(IHDSNode[] children);

}
