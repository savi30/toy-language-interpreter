package main.controller;

import main.exceptions.EmptyExecutionStackException;
import main.model.ProgramState;
import main.model.statement.Statement;
import main.model.util.ExecutionStack;
import main.model.util.Heap;
import main.repository.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InterpreterController {
    private Repository repository;
    private boolean showLog = true;
    private ExecutorService executor = Executors.newFixedThreadPool(8);

    public InterpreterController(Repository repository) {
        this.repository = repository;
    }

    private ProgramState executeStep(ProgramState programState) throws EmptyExecutionStackException {
        if (showLog) {
            this.repository.logProgramState(programState);
        }
        ExecutionStack<Statement> executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty()) {
            throw new EmptyExecutionStackException("Empty Execution Stack\n");
        }
        Statement statement = executionStack.pop();
        return statement.execute(programState);
    }

    public void executeAllSteps() {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> runningPrograms = removeCompletedPrograms(repository.getProgramStates());
        while(!runningPrograms.isEmpty()){
            try {
                collectGarbage(runningPrograms);
                executeOneStepForAllPrograms(runningPrograms);
                runningPrograms = removeCompletedPrograms(repository.getProgramStates());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdownNow();

        List<ProgramState> tempList = repository.getProgramStates();
        tempList.forEach(program->{
            program.getFileTable().forEach((key,value)->{
                try {
                    value.getValue().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        repository.setProgramStates(runningPrograms);
    }

    public void executeOneStepForAllPrograms(List<ProgramState> programStates) throws InterruptedException {
        programStates.forEach(programState -> repository.logProgramState(programState));
        List<Callable<ProgramState>> callableList = programStates
                .stream()
                .map(programState -> (Callable<ProgramState>) programState::oneStep)
                .collect(Collectors.toList());
        List<ProgramState> newProgramStates = executor.invokeAll(callableList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        Logger.getGlobal().log(Level.INFO,e.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStates.addAll(newProgramStates);
        programStates.forEach(programState -> repository.logProgramState(programState));
        repository.setProgramStates(programStates);
    }

    private Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues,
                                                               Map<Integer, Integer> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void collectGarbage(List<ProgramState> programStates) {
        Heap<Integer, Integer> heap = programStates.get(0).getHeap();
        heap.setContent(conservativeGarbageCollector(programStates
                .stream()
                .flatMap(p->p.getSymTableStack().peek().values().stream())
                .collect(Collectors.toList()), heap));
    }

    private List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream().filter(programState -> !programState.isComplete()).collect(Collectors.toList());
    }

    public boolean shouldShowLog() {
        return showLog;
    }

    public void showLog(boolean showLog) {
        this.showLog = showLog;
    }

    public void setInitialProgramState(ProgramState programState) {
        this.repository.getProgramStates().add(programState);
    }

    public Repository getRepository() {
        return repository;
    }
}
