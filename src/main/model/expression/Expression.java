package main.model.expression;

import java.util.Map;

public interface Expression {
    int evaluate(Map<String, Integer> symTable, Map<Integer, Integer> heap);
}
