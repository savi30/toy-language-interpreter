package main.model.statement;

import main.exceptions.IncorrectNumberOfArgumentsException;
import main.exceptions.UndefinedVariableException;
import main.model.ProgramState;
import main.model.expression.Expression;
import main.model.util.SymTable;
import main.model.util.SymTableImpl;

import java.util.List;

public class Call implements Statement {
    String procedureName;
    List<Expression> parameters;

    public Call(String procedureName, List<Expression> parameters){
        this.parameters = parameters;
        this.procedureName = procedureName;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        Statement body = ps.getProcTable().get(procedureName).getValue();
        List<String> params = ps.getProcTable().get(procedureName).getKey();
        if(body == null && params == null){
            throw new UndefinedVariableException();
        }
        SymTable<String, Integer> symTable = ps.getSymTableStack().peek();
        if(parameters.size() != params.size()){
            throw new IncorrectNumberOfArgumentsException();
        }
        SymTable<String, Integer> newSymTable = new SymTableImpl<>();
        for(int i=0;i<params.size();i++){
            newSymTable.put(params.get(i), parameters.get(i).evaluate(symTable, ps.getHeap()));
        }
        ps.getSymTableStack().push(newSymTable);
        ps.getExecutionStack().push(new Return());
        ps.getExecutionStack().push(body);
        return null;
    }

    @Override
    public String toString() {
        return "call " + this.procedureName + "(" + this.parameters.toString() +  ")";
    }
}
