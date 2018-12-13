package main.model.statement;

import main.model.ProgramState;
import main.model.expression.ConstantExpression;
import main.model.expression.Expression;
import main.model.util.Heap;

public class New implements Statement {

    private String variable;
    private Expression expression;


    public New(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        Heap<Integer, Integer> heap = ps.getHeap();
        int address = heap.allocate(expression.evaluate(ps.getSymTable(), ps.getHeap()));
        new AssignmentStatement(variable, new ConstantExpression(address)).execute(ps);
        return null;
    }

    @Override
    public String toString() {
        return "new(" + variable + " ," + expression.toString() + ")";
    }
}
