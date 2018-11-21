package main.model.statement;

import main.model.ProgramState;
import main.model.expression.ConstantExpression;
import main.model.expression.Expression;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements Statement {
    private Expression fileId;
    private String variable;

    public ReadFile(Expression fileId, String variable) {
        this.fileId = fileId;
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        Integer id = fileId.evaluate(ps.getSymTable(), ps.getHeap());
        BufferedReader bufferedReader = ps.getFileTable().get(id).getValue();
        if (bufferedReader != null) {
            try {
                String line = bufferedReader.readLine();
                int value = line != null ? Integer.parseInt(line) : 0;
                new AssignmentStatement(variable, new ConstantExpression(value)).execute(ps);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ps;
    }

    @Override
    public String toString() {
        return "ReadFile(" + fileId.toString() + ", " + variable + ")";
    }
}
