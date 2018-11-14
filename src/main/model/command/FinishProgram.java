package main.model.command;

import main.controller.InterpreterController;

public class FinishProgram extends Command {
    private InterpreterController controller;

    public FinishProgram(String key, String description, InterpreterController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.executeAllSteps();
    }
}
