package main.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import main.model.ProgramState;
import main.model.expression.ArithmeticExpression;
import main.model.expression.ConstantExpression;
import main.model.expression.ReadHeap;
import main.model.expression.VariableExpression;
import main.model.statement.*;
import main.model.util.*;
import main.repository.RepositoryImpl;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class GUIController implements Initializable {
    @FXML
    public TableView<Map.Entry<Integer, Integer>> heapTable;
    @FXML
    public ListView<Integer> output;
    @FXML
    public TableView<Map.Entry<Integer, Pair<String, BufferedReader>>> fileTable;
    @FXML
    public Button oneStep;
    @FXML
    public TableView<Map.Entry<String, Integer>> symbolTable;
    @FXML
    public ListView<ProgramState> programStates;
    @FXML
    public TextField NrProgramStates;
    @FXML
    public ListView<Statement> exeStack;
    @FXML
    public ComboBox<String> availablePrograms;
    @FXML
    public TableView<Map.Entry<String, Pair<List<String>, Statement>>> procTable;

    private InterpreterController controller = new InterpreterController(new RepositoryImpl());
    private List<Statement> statements = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initExamples();
        ObservableList<String> programs = FXCollections
                .observableArrayList(statements.stream().map(statement -> statement.toString()).collect(
                        Collectors.toList()));
        this.availablePrograms.setItems(programs);
        this.availablePrograms.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.loadProgram(newValue);
        });
        configureTableLayout();
    }

    private void initExamples() {
        statements.add(new CompoundStatement(new AssignmentStatement("a",
                new ArithmeticExpression('+', new ConstantExpression(2),
                        new ArithmeticExpression('*', new ConstantExpression(3), new ConstantExpression(5)))),
                new CompoundStatement(new AssignmentStatement("b",
                        new ArithmeticExpression('+', new VariableExpression("a"), new ConstantExpression(1))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("b")),
                                new PrintStatement(new VariableExpression("a"))))));

        statements.add(new CompoundStatement(new AssignmentStatement("a",
                new ArithmeticExpression('-', new ConstantExpression(2), new ConstantExpression(2))),
                new CompoundStatement(new ConditionalStatement(new VariableExpression("a"),
                        new AssignmentStatement("v", new ConstantExpression(2)),
                        new AssignmentStatement("v", new ConstantExpression(3))),
                        new PrintStatement(new VariableExpression("v")))));

        statements.add(new CompoundStatement(new CompoundStatement(new OpenRFile("var_f", "test.txt"),
                new ReadFile(new VariableExpression("var_f"), "a")),
                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new CompoundStatement(
                        new ConditionalStatement(new VariableExpression("a"),
                                new CompoundStatement(new ReadFile(new VariableExpression("var_f"), "a"),
                                        new PrintStatement(new VariableExpression("a"))),
                                new PrintStatement(new ConstantExpression(0))),
                        new CloseRFile(new VariableExpression("var_f"))))));

        statements.add(new CompoundStatement(new CompoundStatement(
                new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(10)),
                        new New("v", new ConstantExpression(20))),
                new CompoundStatement(new New("a", new ConstantExpression(22)),
                        new WriteHeap("a", new ConstantExpression(30)))), new CompoundStatement(
                new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                        new PrintStatement(new ReadHeap("a"))),
                new AssignmentStatement("a", new ConstantExpression((0))))));

        statements.add(new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(6)),
                new CompoundStatement(new WhileStatement(
                        new ArithmeticExpression('-', new VariableExpression("v"), new ConstantExpression(4)),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"),
                                        new ConstantExpression(1))))),
                        new PrintStatement(new VariableExpression("v")))));

        statements.add(new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new New("a", new ConstantExpression(22)),
                        new CompoundStatement(
                                new ForkStatement(new CompoundStatement(new WriteHeap("a", new ConstantExpression(30)),
                                        new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(32)),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeap("a")))))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new PrintStatement(new ReadHeap("a")))))));
        statements.add(new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new ForkStatement(
                                        new CompoundStatement(
                                                new CompoundStatement(
                                                        new AssignmentStatement("v",
                                                                new ArithmeticExpression('-',
                                                                        new VariableExpression("v"),
                                                                        new ConstantExpression(1))),
                                                        new AssignmentStatement("v",
                                                                new ArithmeticExpression('-',
                                                                        new VariableExpression("v"),
                                                                        new ConstantExpression(1)))
                                                ),
                                                new PrintStatement(new VariableExpression("v"))
                                        )
                                ),
                                new CompoundStatement(
                                        new Sleep(10),
                                        new PrintStatement(
                                                new ArithmeticExpression('*',
                                                        new VariableExpression("v"),
                                                        new ConstantExpression(10))
                                        ))
                        )
                )
        );

        statements.add(new CompoundStatement(
                new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(2)),
                        new AssignmentStatement("w", new ConstantExpression(5))
                ),
                new CompoundStatement(
                        new Call("sum", Arrays.asList(new ArithmeticExpression(
                                '*',
                                new VariableExpression("v"),
                                new ConstantExpression(10)
                        ), new VariableExpression("w"))),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new ForkStatement(
                                        new CompoundStatement(
                                                new Call("product",
                                                        Arrays.asList(
                                                                new VariableExpression("v"),
                                                                new VariableExpression("w")
                                                        )),
                                                new ForkStatement(
                                                        new Call("sum",
                                                                Arrays.asList(
                                                                        new VariableExpression("v"),
                                                                        new VariableExpression("w")
                                                                ))
                                                )
                                        )
                                )
                        )
                )
        ));
    }

    public void executeOneStep(ActionEvent event) {
        try {
            this.controller.executeOneStepForAllPrograms(controller.getRepository().getProgramStates());
            this.onProgramSelected(null);
        } catch (InterruptedException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            oneStep.setDisable(true);
            oneStep.disarm();
        }
    }

    public void onProgramSelected(MouseEvent mouseEvent) {
        if (this.programStates.getSelectionModel().getSelectedIndex() < 0) {
            this.reload(0);
            return;
        }
        this.reload(this.programStates.getSelectionModel().getSelectedIndex());
    }

    private void loadProgram(String programString) {
        Statement statement = this.statements.stream().filter(s -> s.toString().equals(programString)).findFirst()
                .get();
        ProcTable<String, Pair<List<String>, Statement>> procTable = new ProcTableImpl<>();
        Procedure sum = new Procedure("sum",
                new Pair<>(Arrays.asList("a", "b"),
                        new CompoundStatement(
                                new AssignmentStatement("v",
                                        new ArithmeticExpression('+',
                                                new VariableExpression("a"),
                                                new VariableExpression("b")
                                        )),
                                new PrintStatement(new VariableExpression("v"))
                        )));
        Procedure product = new Procedure("product",
                new Pair<>(Arrays.asList("a", "b"),
                        new CompoundStatement(
                                new AssignmentStatement("v",
                                        new ArithmeticExpression('*',
                                                new VariableExpression("a"),
                                                new VariableExpression("b")
                                        )),
                                new PrintStatement(new VariableExpression("v"))
                        )));
        procTable.put(sum.getProcedureName(), sum.getProcedureBody());
        procTable.put(product.getProcedureName(), product.getProcedureBody());
        ProgramState programState = new ProgramState(new ExecutionStackImpl<>(),
                new OutputImpl<>(),
                new Stack<>(),
                new FileTableImpl<>(),
                new HeapImpl(),
                procTable,
                statement);
        programState.getSymTableStack().push(new SymTableImpl<>());
        this.controller.setInitialProgramState(programState);
        this.reload(0);
    }

    private void reload(int programStateIndex) {
        ProgramState currentProgramState = controller.getRepository().getProgramStates().get(programStateIndex);

        heapTable.setItems(FXCollections.observableArrayList(currentProgramState.getHeap().entrySet()));
        output.setItems(FXCollections.observableArrayList(currentProgramState.getOutput()));
        fileTable.setItems(FXCollections.observableArrayList(currentProgramState.getFileTable().entrySet()));
        symbolTable
                .setItems(FXCollections.observableArrayList(currentProgramState.getSymTableStack().peek().entrySet()));
        programStates.setItems(FXCollections.observableArrayList(controller.getRepository().getProgramStates()));
        exeStack.setItems(FXCollections.observableArrayList(currentProgramState.getExecutionStack()));
        procTable.setItems(FXCollections.observableArrayList(currentProgramState.getProcTable().entrySet()));
        this.NrProgramStates.textProperty()
                .setValue(String.valueOf(this.controller.getRepository().getProgramStates().size()));

    }

    @SuppressWarnings("unchecked")
    private void configureTableLayout() {
        TableColumn<Map.Entry<Integer, Integer>, Integer> address = new TableColumn<>("Address");
        address.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey()));

        TableColumn<Map.Entry<Integer, Integer>, Integer> heapValue = new TableColumn<>("Value");
        heapValue.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));

        heapTable.getColumns().addAll(address, heapValue);

        // config the file table
        TableColumn<Map.Entry<Integer, Pair<String, BufferedReader>>, Integer> identifier = new TableColumn<>(
                "Descriptor");
        identifier.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey()));

        TableColumn<Map.Entry<Integer, Pair<String, BufferedReader>>, String> fileName = new TableColumn<>("File");
        fileName.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().getValue().toString()));

        fileTable.getColumns().addAll(identifier, fileName);

        // symbol table
        TableColumn<Map.Entry<String, Integer>, String> variableNameColumn = new TableColumn<>("Variable Name");
        variableNameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey()));

        TableColumn<Map.Entry<String, Integer>, Integer> varValueColumn = new TableColumn<>("Value");
        varValueColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue()));

        symbolTable.getColumns().addAll(variableNameColumn, varValueColumn);

        TableColumn<Map.Entry<String, Pair<List<String>, Statement>>, String> procNameCol = new TableColumn<>(
                "Name & params");
        procNameCol.setCellValueFactory(param -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(param.getValue().getKey()).append("(");
            param.getValue().getValue().getKey().forEach(p->{
               stringBuilder.append(p).append(", ");
            });
            stringBuilder.append(")");
            return new ReadOnlyObjectWrapper<>(stringBuilder.toString());
        });

        TableColumn<Map.Entry<String, Pair<List<String>, Statement>>, String> procBodyCol = new TableColumn<>("body");
        procBodyCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().getValue().toString()));

        procTable.getColumns().addAll(procNameCol, procBodyCol);

        heapTable.setItems(FXCollections.observableArrayList());
        output.setItems(FXCollections.observableArrayList());
        fileTable.setItems(FXCollections.observableArrayList());
        symbolTable.setItems(FXCollections.observableArrayList());
        programStates.setItems(FXCollections.observableArrayList());
        exeStack.setItems(FXCollections.observableArrayList());
    }
}