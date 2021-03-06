package main.model.statement;

import main.exceptions.UndefinedVariableException;
import main.model.ProgramState;
import main.model.expression.Expression;

public class WriteHeap implements Statement {
    private String variable;
    private Expression expression;

    public WriteHeap(String variable, Expression expression) {
        this.expression = expression;
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        Integer address = ps.getSymTableStack().peek().get(variable);
        if (address == null) {
            throw new UndefinedVariableException();
        }
        ps.getHeap().put(address, expression.evaluate(ps.getSymTableStack().peek(), ps.getHeap()));
        return null;
    }

    @Override
    public String toString() {
        return "WriteHeap(" + variable + ", " + expression.toString() + ")";
    }
}
