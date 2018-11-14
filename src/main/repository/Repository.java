package main.repository;

import main.model.ProgramState;

public interface Repository {
    ProgramState getCurrentProgram();

    void serCurrentProgram(ProgramState programState);

    void logProgramState();
}
