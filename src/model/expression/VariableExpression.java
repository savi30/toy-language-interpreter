package model.expression;

import java.util.Map;

public class VariableExpression implements Expression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(Map<String, Integer> symTable) {
        if (symTable.containsKey(variable)) {
            return symTable.get(variable);
        }
        throw new RuntimeException("Variable is not defined");
    }

    @Override
    public String toString() {
        return variable;
    }
}
