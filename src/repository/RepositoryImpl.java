package repository;

import model.ProgramState;

public class RepositoryImpl implements Repository {
    private ProgramState currentProgram;

    @Override
    public ProgramState getCurrentProgram() {
        return currentProgram;
    }

    @Override
    public void serCurrentProgram(ProgramState programState) {
        this.currentProgram = programState;
    }
}
