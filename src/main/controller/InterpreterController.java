package main.controller;

import main.exceptions.EmptyExecutionStackException;
import main.model.ProgramState;
import main.model.statement.Statement;
import main.model.util.ExecutionStack;
import main.repository.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class InterpreterController {
    private Repository repository;
    private boolean showLog = true;

    public InterpreterController(Repository repository) {
        this.repository = repository;
    }

    private ProgramState executeStep(ProgramState programState) throws EmptyExecutionStackException {
        if (showLog) {
            this.repository.logProgramState();
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
                collectGarbage(programState);
            } catch (EmptyExecutionStackException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    private Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues,
                                                               Map<Integer, Integer> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void collectGarbage(ProgramState programState) {
        programState
                .getHeap()
                .setContent(conservativeGarbageCollector(programState.getSymTable().values(), programState.getHeap()));
    }

    public boolean shouldShowLog() {
        return showLog;
    }

    public void showLog(boolean showLog) {
        this.showLog = showLog;
    }

    public void setInitialProgramState(ProgramState programState) {
        this.repository.serCurrentProgram(programState);
    }
}
