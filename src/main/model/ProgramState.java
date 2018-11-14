package main.model;

import javafx.util.Pair;
import main.model.statement.Statement;
import main.model.util.*;

import java.io.BufferedReader;

public class ProgramState {
    private ExecutionStack<Statement> executionStack = new ExecutionStackImpl<>();
    private Output<Integer> output = new OutputImpl<>();
    private SymTable<String, Integer> symTable = new SymTableImpl<>();
    private FileTable<Integer, Pair<String, BufferedReader>> fileTable = new FileTableImpl<>();
    private Statement program;

    public ProgramState(ExecutionStack<Statement> executionStack,
                        Output<Integer> output,
                        SymTable<String, Integer> symTable,
                        FileTable<Integer, Pair<String, BufferedReader>> fileTable,
                        Statement program) {
        this.executionStack = executionStack;
        this.symTable = symTable;
        this.output = output;
        this.fileTable = fileTable;
        this.program = program;
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

    @Override
    public String toString() {
        return "Execution stack:\n"
                + executionStack.toString()
                + "\nSymTable:\n"
                + symTable.toString()
                + "\nOutput:\n"
                + output.toString()
                + "\nFile table\n"
                + fileTable.toString()
                + "\nProgram:\n"
                + program.toString()
                + "\n";
    }
}