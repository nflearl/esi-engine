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
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new BooleanExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(ComparisonExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new ComparisonExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(VarOrLiteral.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new VarOrLiteral(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(VariableExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new VariableExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(VariableExpression.ALT_NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new VariableExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(VarWithArg.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new VarWithArg(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(VarWithArg.ALT_NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new VarWithArg(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(LiteralExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new LiteralExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(FunctionExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new FunctionExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(FunctionArgumentExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new FunctionArgumentExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(AndExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new AndExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(OrExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new OrExpression(ExpressionBuilder.this, curNode);
            }
        });
        exprMap.put(NotExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new NotExpression(ExpressionBuilder.this, curNode);
            }
        });

        exprMap.put(VarID.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new VarID(ExpressionBuilder.this, curNode);
            }
        });

        exprMap.put(NumberExpression.NAME, new ExpressionFactory() {
            @Override
            public ESIExpression build(ExpressionBuilder eb, IHDSNode curNode) {
                return new NumberExpression(ExpressionBuilder.this, curNode);
            }
        });
    }

    public ESIExpression build(IHDSNode root) {
        ExpressionFactory factory = exprMap.get(root.getName());
        return (factory == null) ? new DefaultExpressionHandler(this, root) : factory.build(this, root);
    }

    private interface ExpressionFactory {
        ESIExpression build(ExpressionBuilder eb, IHDSNode curNode);
    }

    public ESIContext getEsiContext() {
        return esiContext;
    }
}
