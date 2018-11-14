package main.view;

import main.model.ProgramState;
import main.model.command.Command;
import main.model.expression.ArithmeticExpression;
import main.model.expression.ConstantExpression;
import main.model.expression.VariableExpression;
import main.model.statement.*;
import main.model.util.ExecutionStackImpl;
import main.model.util.FileTableImpl;
import main.model.util.OutputImpl;
import main.model.util.SymTableImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command com : commands.values()) {
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}