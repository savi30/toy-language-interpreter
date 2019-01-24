package main.model.statement;

import javafx.util.Pair;
import main.model.ProgramState;

import java.util.List;

public class Procedure implements Statement{

    private String procedureName;

    private Pair<List<String>, Statement> procedureBody;

    public Procedure(String procedureName, Pair<List<String>, Statement> procedureBody){
        this.procedureBody = procedureBody;
        this.procedureName = procedureName;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        ps.getProcTable().put(this.procedureName, this.procedureBody);
        return null;
    }


    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Pair<List<String>, Statement> getProcedureBody() {
        return procedureBody;
    }

    public void setProcedureBody(
            Pair<List<String>, Statement> procedureBody) {
        this.procedureBody = procedureBody;
    }
}
