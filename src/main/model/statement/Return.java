package main.model.statement;

import main.model.ProgramState;

public class Return implements Statement {


    @Override
    public ProgramState execute(ProgramState ps) {
        ps.getSymTableStack().pop();
        return null;
    }

    @Override
    public String toString() {
        return "return";
    }
}
