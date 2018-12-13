package main.repository;

import main.model.ProgramState;

import java.util.List;

public interface Repository {

    List<ProgramState> getProgramStates();

    void setProgramStates(List<ProgramState> programStates);

    void logProgramState(ProgramState programState);
}
