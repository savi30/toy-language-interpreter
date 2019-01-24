package main.model.statement;

import main.model.ProgramState;
import main.model.expression.Expression;

public class WhileStatement implements Statement {

    private Expression expression;
    private Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(final ProgramState ps) {
        if (expression.evaluate(ps.getSymTableStack().peek(), ps.getHeap()) != 0) {
            ps.getExecutionStack().push(this);
            ps.getExecutionStack().push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") do {" + statement.toString() + "}";
    }
}
