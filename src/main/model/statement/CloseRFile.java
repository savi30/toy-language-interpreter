package main.model.statement;

import main.model.ProgramState;
import main.model.expression.Expression;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements Statement {
    private Expression fileId;

    public CloseRFile(Expression fileId) {
        this.fileId = fileId;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        int value = fileId.evaluate(ps.getSymTable(), ps.getHeap());
        BufferedReader bufferedReader = ps.getFileTable().get(value).getValue();
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
                ps.getFileTable().remove(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ps;
    }

    @Override
    public String toString() {
        return "CloseFile(" + fileId.toString() + ")";
    }
}
