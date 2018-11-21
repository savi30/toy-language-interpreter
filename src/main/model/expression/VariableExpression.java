package main.model.expression;

import main.exceptions.UndefinedVariableException;

import java.util.Map;

public class VariableExpression implements Expression {
    private String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(Map<String, Integer> symTable, Map<Integer, Integer> heap) {
        if (symTable.containsKey(variable)) {
            return symTable.get(variable);
        }
        throw new UndefinedVariableException();
    }

    @Override
    public String toString() {
        return variable;
    }
}
