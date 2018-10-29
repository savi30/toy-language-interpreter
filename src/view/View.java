package view;

import controller.InterpreterController;
import model.ProgramState;
import model.expression.ArithmenticExpression;
import model.expression.ConstantExpression;
import model.expression.VariableExpression;
import model.statement.*;
import model.util.ExecutionStackImpl;
import model.util.OutputImpl;
import model.util.SymTableImpl;

import java.util.Scanner;

public class View {
    private InterpreterController controller;

    public View(InterpreterController controller) {
        this.controller = controller;
    }

    private void displayMenu(String message) {
        System.out.print(message);
    }

    private String mainMenu() {
        String message = "";
        message += "1.Input a program\n";
        message += "2.Execute one step\n";
        message += "3.Complete program evaluation\n";
        message += "0.Exit\n";
        return message;
    }

    public void start() {
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            this.displayMenu(this.mainMenu());
            opt = scanner.nextInt();
            switch (opt) {
                case 1:
                    loadHardcodedInputs();
                    break;
                case 2:
                    controller.executeOneStep();
                    break;
                case 3:
                    controller.executeAllSteps();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void loadHardcodedInputs() {
        Statement example1 = new CompoundStatement(
                new AssignmentStatement("a",
                        new ArithmenticExpression('+',
                                new ConstantExpression(2),
                                new ArithmenticExpression('*',
                                        new ConstantExpression(3),
                                        new ConstantExpression(5)))),
                new CompoundStatement(
                        new AssignmentStatement("b",
                                new ArithmenticExpression('+',
                                        new VariableExpression("a"),
                                        new ConstantExpression(1))),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("b")),
                                new PrintStatement(new VariableExpression("a"))
                        )));

        Statement example2 = new CompoundStatement(
                new AssignmentStatement("a",
                        new ArithmenticExpression('-',
                                new ConstantExpression(2),
                                new ConstantExpression(2))),
                new CompoundStatement(
                        new ConditionalStatement(
                                new VariableExpression("a"),
                                new AssignmentStatement("v",
                                        new ConstantExpression(2)),
                                new AssignmentStatement("v",
                                        new ConstantExpression(3))),
                        new PrintStatement(new VariableExpression("v"))));

        ProgramState programState = new ProgramState(new ExecutionStackImpl<>(),
                new OutputImpl<>(),
                new SymTableImpl<>(),
                example2);
        controller.setInitialProgramState(programState);
    }
}
