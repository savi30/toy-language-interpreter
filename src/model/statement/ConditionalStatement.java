package model.statement;

import model.ProgramState;
import model.expression.Expression;
import model.util.ExecutionStack;

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
        if (expression.evaluate(ps.getSymTable()) != 0) {
            executionStack.push(statement1);
        } else {
            executionStack.push(statement2);
        }
        return ps;
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
