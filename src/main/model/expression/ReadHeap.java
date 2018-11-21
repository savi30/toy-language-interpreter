package main.model.expression;

import main.exceptions.UndefinedVariableException;

import java.util.Map;

public class ReadHeap implements Expression {
    private String variable;

    public ReadHeap(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(Map<String, Integer> symTable, Map<Integer, Integer> heap) {
        Integer address = symTable.get(variable);
        if (address == null) {
            throw new UndefinedVariableException();
        }
        Integer value = heap.get(address);
        if (value == null) {
            throw new UndefinedVariableException();
        }
        return value;
    }

    @Override
    public String toString() {
        return "ReadHeap(" + variable + ")";
    }
}
