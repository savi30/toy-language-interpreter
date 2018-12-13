package main.repository;

import main.model.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RepositoryImpl implements Repository {
    private String logFilePath;
    private List<ProgramState> programStates = new ArrayList<>();

    public RepositoryImpl() {
    }

    public RepositoryImpl(List<ProgramState> programState) {
        this.programStates = programState;
    }

    public RepositoryImpl(List<ProgramState> programState, String logFilePath) {
        this.programStates = programState;
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return this.programStates;
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramState(ProgramState programState) {
        if (logFilePath == null) {
            this.logFilePath = initLogPath();
        }
        try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(logFilePath, true)))) {
            writer.print(programState);
            writer.print(System.lineSeparator());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String initLogPath() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String fileName;
        while (true) {
            System.out.print("Where would you want to log the program?\n");
            try {
                fileName = scanner.next("^[\\w\\s_-]+$");
                return "./logs/" + fileName + ".txt";
            } catch (InputMismatchException e) {
                System.out.print("Incorrect filename. Filename must contain only letter, digits or \"_\",\"-\"");
                initLogPath();
            }
        }
    }
}
