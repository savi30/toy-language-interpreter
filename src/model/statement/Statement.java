package model.statement;

import model.ProgramState;

public interface Statement {
    ProgramState execute(ProgramState ps);
}
