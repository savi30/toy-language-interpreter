package repository;

import model.ProgramState;

public interface Repository {
    ProgramState getCurrentProgram();

    void serCurrentProgram(ProgramState programState);
}
