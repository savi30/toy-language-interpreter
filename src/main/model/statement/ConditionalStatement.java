package main.model.statement;

import main.model.ProgramState;
import main.model.expression.Expression;
import main.model.util.ExecutionStack;

public class ConditionalStatement implements Statement {

    private Statement statement1, statement2;
    private Expression expression;

    public ConditionalStatement(Expression expression,
                                Statement statement1,
                                Statement statement2) {
        this.expression = expression;
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        ExecutionStack<Statement> executionStack = ps.getExecutionStack();
        if (expression.evaluate(ps.getSymTable(), ps.getHeap()) != 0) {
            executionStack.push(statement1);
        } else {
            executionStack.push(statement2);
        }
        return null;
    }

    @Override
    public String toString() {
        return "if("
                + expression.toString()
                + ") then("
                + statement1.toString()
                + ") else("
                + statement2.toString()
                + ")";
    }
}
