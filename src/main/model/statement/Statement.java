package main.model.statement;

import main.model.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState ps);
}
