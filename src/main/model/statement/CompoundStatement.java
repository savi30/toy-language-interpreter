package main.model.statement;

import main.model.ProgramState;
import main.model.util.ExecutionStack;

public class CompoundStatement implements Statement {
    private Statement statement1, statement2;

    public CompoundStatement(Statement statement1, Statement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        ExecutionStack<Statement> executionStack = ps.getExecutionStack();
        executionStack.push(statement2);
        executionStack.push(statement1);
        return null;
    }

    @Override
    public String toString() {
        return "(" + statement1.toString() + ", " + statement2.toString() + ")";
    }
}
