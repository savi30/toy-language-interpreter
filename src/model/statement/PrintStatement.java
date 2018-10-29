package model.statement;

import model.ProgramState;
import model.expression.Expression;
import model.util.Output;

public class PrintStatement implements Statement {

    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        Output<Integer> output = ps.getOutput();
        output.addFirst(expression.evaluate(ps.getSymTable()));
        return ps;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
