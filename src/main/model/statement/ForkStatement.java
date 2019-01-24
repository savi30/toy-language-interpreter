package main.model.statement;

import main.model.ProgramState;
import main.model.util.ExecutionStack;
import main.model.util.ExecutionStackImpl;
import main.model.util.SymTable;
import main.model.util.SymTableImpl;

import java.util.Stack;

public class ForkStatement implements Statement {
    Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        Stack<SymTable<String, Integer>> newSymTableStack = new Stack<>();

        ps.getSymTableStack().forEach((symT -> {
            SymTable<String, Integer> newSymT = new SymTableImpl<>();
            symT.forEach((key, value) -> {
                newSymT.put(String.valueOf(key), value);
            });
            newSymTableStack.push(newSymT);
        }));

        return new ProgramState(
                new ExecutionStackImpl<>(),
                ps.getOutput(),
                newSymTableStack,
                ps.getFileTable(),
                ps.getHeap(),
                ps.getProcTable(),
                statement
        );
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
