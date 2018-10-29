package model.expression;

import java.util.Map;

public interface Expression {
    int evaluate(Map<String, Integer> symTable);
}
