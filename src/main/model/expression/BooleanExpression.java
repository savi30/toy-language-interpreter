package main.model.expression;

import main.exceptions.UndefinedOperationException;

import java.util.Map;

public class BooleanExpression implements Expression {

    private Expression expression1;
    private Expression expression2;
    private String operator;

    public BooleanExpression(String operator, Expression expression1, Expression expression2) {
        this.operator = operator;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public int evaluate(final Map<String, Integer> symTable, final Map<Integer, Integer> heap) {
        switch (operator) {
            case "<":
                return expression1.evaluate(symTable, heap) - expression2.evaluate(symTable, heap) < 0 ? 1 : 0;
            case "<=":
                return expression1.evaluate(symTable, heap) - expression2.evaluate(symTable, heap) <= 0 ? 1 : 0;
            case "==":
                return expression1.evaluate(symTable, heap) - expression2.evaluate(symTable, heap) == 0 ? 1 : 0;
            case "!=":
                return expression1.evaluate(symTable, heap) - expression2.evaluate(symTable, heap) != 0 ? 1 : 0;
            case ">":
                return expression1.evaluate(symTable, heap) - expression2.evaluate(symTable, heap) > 0 ? 1 : 0;
            case ">=":
                return expression1.evaluate(symTable, heap) - expression2.evaluate(symTable, heap) >= 0 ? 1 : 0;
            default:
                throw new UndefinedOperationException();
        }
    }

    @Override
    public String toString() {
        return expression1.toString() + operator + expression2.toString();
    }
}
