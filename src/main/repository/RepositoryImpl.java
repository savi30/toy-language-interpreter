package main.repository;

import main.model.ProgramState;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RepositoryImpl implements Repository {
    private String logFilePath;
    private ProgramState currentProgram;

    public RepositoryImpl() {
    }

    public RepositoryImpl(ProgramState programState) {
        this.currentProgram = programState;
    }

    public RepositoryImpl(ProgramState programState, String logFilePath) {
        this.currentProgram = programState;
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getCurrentProgram() {
        return currentProgram;
    }

    @Override
    public void serCurrentProgram(ProgramState programState) {
        this.currentProgram = programState;
    }

    @Override
    public void logProgramState() {
        if (logFilePath == null) {
            this.logFilePath = initLogPath();
        }
        try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(logFilePath, true)))) {
            writer.println(currentProgram.toString());
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
