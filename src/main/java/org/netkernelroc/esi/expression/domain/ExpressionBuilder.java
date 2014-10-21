package org.netkernelroc.esi.expression.domain;

import org.netkernel.layer0.representation.IHDSNode;
import org.netkernelroc.esi.rendering.ESIContext;

import java.util.HashMap;
import java.util.Map;

public class ExpressionBuilder {

    private final Map<String, ExpressionFactory> exprMap = new HashMap<String, ExpressionFactory>(89);

    private final ESIContext esiContext;

    public ExpressionBuilder(ESIContext esiContext) {
        this.esiContext = esiContext;
        mapInit();
    }

    private void mapInit() {
        exprMap.put(BooleanExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new BooleanExpression(ExpressionBuilder.this);
            }
        });
        exprMap.put(ComparisonExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new ComparisonExpression(ExpressionBuilder.this);
            }
        });
        exprMap.put(VarOrLiteral.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new VarOrLiteral(ExpressionBuilder.this);
            }
        });
        exprMap.put(VariableExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new VariableExpression(ExpressionBuilder.this);
            }
        });
        exprMap.put(VariableExpression.ALT_NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new VariableExpression(ExpressionBuilder.this);
            }
        });
        exprMap.put(LiteralExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new LiteralExpression(ExpressionBuilder.this);
            }
        });
        exprMap.put(FunctionExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new FunctionExpression(ExpressionBuilder.this);
            }
        });
        exprMap.put(FunctionArgumentExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new FunctionArgumentExpression(ExpressionBuilder.this);
            }
        });
        exprMap.put(AndExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb) {
                return new AndExpression(ExpressionBuilder.this);
            }
        });
    }

    public ESIExpression build(IHDSNode root) {
        ExpressionFactory factory = exprMap.get(root.getName());
        return (factory == null) ? new DefaultExpressionHandler(this) : factory.build(this);
    }

    private interface ExpressionFactory {
        ESIExpression build(ExpressionBuilder eb);
    }

    public ESIContext getEsiContext() {
        return esiContext;
    }
}
