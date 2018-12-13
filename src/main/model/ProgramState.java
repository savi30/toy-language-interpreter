package main.model;

import javafx.util.Pair;
import main.model.statement.Statement;
import main.model.util.*;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.EmptyStackException;

public class ProgramState {
    private ExecutionStack<Statement> executionStack = new ExecutionStackImpl<>();
    private Output<Integer> output = new OutputImpl<>();
    private SymTable<String, Integer> symTable = new SymTableImpl<>();
    private FileTable<Integer, Pair<String, BufferedReader>> fileTable = new FileTableImpl<>();
    private Heap<Integer, Integer> heap = new HeapImpl();
    private Statement program;
    private Integer id;

    public ProgramState(ExecutionStack<Statement> executionStack,
                        Output<Integer> output,
                        SymTable<String, Integer> symTable,
                        FileTable<Integer, Pair<String, BufferedReader>> fileTable,
                        Heap<Integer, Integer> heap,
                        Statement program, Integer id) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.output = output;
        this.fileTable = fileTable;
        this.program = program;
        this.heap = heap;
        this.id = id;
        executionStack.push(program);
    }

    public ExecutionStack<Statement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(ExecutionStack<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public Output<Integer> getOutput() {
        return output;
    }

    public void setOutput(Output<Integer> output) {
        this.output = output;
    }

    public SymTable<String, Integer> getSymTable() {
        return symTable;
    }

    public void setSymTable(SymTable<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public Statement getProgram() {
        return program;
    }

    public void setProgram(Statement program) {
        this.program = program;
    }

    public FileTable<Integer, Pair<String, BufferedReader>> getFileTable() {
        return fileTable;
    }

    public void setFileTable(FileTable<Integer, Pair<String, BufferedReader>> fileTable) {
        this.fileTable = fileTable;
    }

    public Heap<Integer, Integer> getHeap() {
        return heap;
    }

    public void setHeap(Heap<Integer, Integer> heap) {
        this.heap = heap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isComplete() {
        return this.executionStack.isEmpty();
    }

    public ProgramState oneStep() {
        try {
            return executionStack.pop().execute(this);
        } catch (EmptyStackException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Program state ")
                .append(this.id)
                .append("\n")
                .append(symTable.toString())
                .append("\n")
                .append(executionStack.toString())
                .append("\n")
                .append(output.toString())
                .append("\n")
                .append(fileTable.toString())
                .append("\n")
                .append(heap.toString())
                .append("\n")
                .append(program.toString())
                .append("\n")
                .append(lineSeparator())
                .toString();
    }

    private String lineSeparator() {
        char[] separator = new char[50];
        Arrays.fill(separator, '-');
        return String.valueOf(separator) + "\n";
    }
}
