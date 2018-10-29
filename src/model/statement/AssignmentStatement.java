package model.statement;

import model.ProgramState;
import model.expression.Expression;
import model.util.SymTable;

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
        symTable.put(this.variable, this.expression.evaluate(symTable));
        return ps;
    }

    @Override
    public String toString() {
        return variable + " = " + expression.toString();
    }
}
