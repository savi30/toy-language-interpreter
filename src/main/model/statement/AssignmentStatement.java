package main.model.statement;

import main.model.ProgramState;
import main.model.expression.Expression;
import main.model.util.SymTable;

public class AssignmentStatement implements Statement {
    private String variable;
    private Expression expression;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        SymTable<String, Integer> symTable = ps.getSymTable();
        symTable.put(this.variable, this.expression.evaluate(ps.getSymTable(), ps.getHeap()));
        return ps;
    }

    @Override
    public String toString() {
        return variable + " = " + expression.toString();
    }
}
