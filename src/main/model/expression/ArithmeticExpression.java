package main.model.expression;

import main.exceptions.UndefinedOperationException;

import java.util.Map;

public class ArithmeticExpression implements Expression {

    private Expression expression1, expression2;
    private char operator;

    public ArithmeticExpression(char operator,
                                 Expression expression1,
                                 Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    public int evaluate(Map<String, Integer> symTable) {
        int a = this.expression1.evaluate(symTable);
        int b = this.expression2.evaluate(symTable);

        switch (this.operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by 0");
                }
                return a / b;

            default:
                throw new UndefinedOperationException();
        }
    }

    @Override
    public String toString() {
        return "(" + expression1.toString() + operator + expression2.toString() + ")";
    }
}
