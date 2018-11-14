import main.controller.InterpreterController;
import main.model.ProgramState;
import main.model.command.Exit;
import main.model.command.FinishProgram;
import main.model.command.RunStep;
import main.model.expression.ArithmeticExpression;
import main.model.expression.ConstantExpression;
import main.model.expression.VariableExpression;
import main.model.statement.*;
import main.model.util.ExecutionStackImpl;
import main.model.util.FileTableImpl;
import main.model.util.OutputImpl;
import main.model.util.SymTableImpl;
import main.repository.Repository;
import main.repository.RepositoryImpl;
import main.view.TextMenu;

public class Application {

    private static InterpreterController controller;

    public static void main(String... args) {
        Repository repository = new RepositoryImpl();
        controller = new InterpreterController(repository);
        TextMenu textMenu = setupMenu();
        setupProgram();
        textMenu.show();
    }

    private static TextMenu setupMenu() {
        TextMenu menu = new TextMenu();
        menu.addCommand(new Exit("0", "exit"));
        menu.addCommand(new RunStep("1", "run one step", controller));
        menu.addCommand(new FinishProgram("2", "run all steps", controller));
        return menu;
    }

    private static void setupProgram() {
        Statement example1 = new CompoundStatement(
                new AssignmentStatement("a",
                        new ArithmeticExpression('+',
                                new ConstantExpression(2),
                                new ArithmeticExpression('*',
                                        new ConstantExpression(3),
                                        new ConstantExpression(5)))),
                new CompoundStatement(
                        new AssignmentStatement("b",
                                new ArithmeticExpression('+',
                                        new VariableExpression("a"),
                                        new ConstantExpression(1))),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("b")),
                                new PrintStatement(new VariableExpression("a"))
                        )));

        Statement example2 = new CompoundStatement(
                new AssignmentStatement("a",
                        new ArithmeticExpression('-',
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

        Statement example3 = new CompoundStatement(
                new CompoundStatement(
                        new OpenRFile("var_f", "test.txt"),
                        new ReadFile(new VariableExpression("var_f"), "a")),
                new CompoundStatement(
                        new PrintStatement(new VariableExpression("a")),
                        new CompoundStatement(
                                new ConditionalStatement(
                                        new VariableExpression("a"),
                                        new CompoundStatement(
                                                new ReadFile(new VariableExpression("var_f"), "a"),
                                                new PrintStatement(new VariableExpression("a"))),
                                        new PrintStatement(new ConstantExpression(0))),
                                new CloseRFile(new VariableExpression("var_f")))));

        ProgramState programState = new ProgramState(new ExecutionStackImpl<>(),
                new OutputImpl<>(),
                new SymTableImpl<>(),
                new FileTableImpl<>(),
                example3);
        controller.setInitialProgramState(programState);
    }
}
