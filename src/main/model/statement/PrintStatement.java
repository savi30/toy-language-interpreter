package main.model.statement;

import main.model.ProgramState;
import main.model.expression.Expression;
import main.model.util.Output;

public class PrintStatement implements Statement {

    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        Output<Integer> output = ps.getOutput();
        output.addFirst(expression.evaluate(ps.getSymTableStack().peek(), ps.getHeap()));
        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
