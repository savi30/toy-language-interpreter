package main.model.statement;

import main.model.ProgramState;

public class Sleep implements Statement {
    int nrOfSeconds;

    public Sleep(int nrOfSeconds){
        this.nrOfSeconds = nrOfSeconds;
    }

    @Override
    public ProgramState execute(ProgramState ps) {
        if(nrOfSeconds > 0){
            ps.getExecutionStack().push(new Sleep(nrOfSeconds - 1));
        }
        return null;
    }

    @Override
    public String toString() {
        return "sleep(" + nrOfSeconds + ")";
    }
}
