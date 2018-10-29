package controller;

import exceptions.EmptyExecutionStackException;
import model.ProgramState;
import model.statement.Statement;
import model.util.ExecutionStack;
import repository.Repository;

public class InterpreterController {
    private Repository repository;
    private boolean displayOn;

    public InterpreterController(Repository repository) {
        this.repository = repository;
    }

    private ProgramState executeStep(ProgramState programState) throws EmptyExecutionStackException {
        if (displayOn) {
            System.out.print(programState.toString());
        }
        ExecutionStack<Statement> executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty()) {
            throw new EmptyExecutionStackException("Empty Execution Stack\n");
        }
        Statement statement = executionStack.pop();
        return statement.execute(programState);
    }

    public void executeOneStep() {
        ProgramState programState = repository.getCurrentProgram();
        try {
            executeStep(programState);
        } catch (EmptyExecutionStackException e) {
            System.out.print(e.getMessage());
        }
    }

    public void executeAllSteps() {
        ProgramState programState = repository.getCurrentProgram();
        while (true) {
            try {
                executeStep(programState);

            } catch (EmptyExecutionStackException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    public boolean isDisplayOn() {
        return displayOn;
    }

    public void setDisplayOn(boolean displayOn) {
        this.displayOn = displayOn;
    }

    public void setInitialProgramState(ProgramState programState) {
        this.repository.serCurrentProgram(programState);
    }
}
