package main.model.statement;

import main.model.ProgramState;
import main.model.util.ExecutionStackImpl;
import main.model.util.SymTable;
import main.model.util.SymTableImpl;

public class ForkStatement implements Statement {
    Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        SymTable<String, Integer> newSymTable = new SymTableImpl<>();
        ps.getSymTable().forEach((key, value) -> newSymTable.put(String.valueOf(key), value));

        return new ProgramState(
                new ExecutionStackImpl<>(),
                ps.getOutput(),
                newSymTable,
                ps.getFileTable(),
                ps.getHeap(),
                statement
        );
    }
}
