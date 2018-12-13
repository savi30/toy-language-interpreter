package main.model.statement;

import javafx.util.Pair;
import main.exceptions.FileAlreadyOpenedException;
import main.model.ProgramState;
import main.model.expression.ConstantExpression;
import main.model.util.FileTable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class OpenRFile implements Statement {
    private String filename;
    private String variable;

    public OpenRFile(String variable, String filename) {
        this.filename = filename;
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        FileTable<Integer, Pair<String, BufferedReader>> fileTable = ps.getFileTable();
        fileTable.values()
                .stream()
                .filter(value -> value.getKey().equals(this.filename)).findFirst()
                .ifPresent((value)->{throw new FileAlreadyOpenedException();});

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            Integer id = FileTable.getId();
            fileTable.put(id, new Pair<>(this.filename, bufferedReader));
            new AssignmentStatement(this.variable, new ConstantExpression(id)).execute(ps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "openRFile(" + variable + ", " + filename + ")";
    }
}
