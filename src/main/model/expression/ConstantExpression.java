package main.model.expression;

import java.util.Map;

public class ConstantExpression implements Expression {
    private int value;

    public ConstantExpression(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(Map<String, Integer> symTable) {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
