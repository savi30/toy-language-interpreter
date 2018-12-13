package main.model.command;

import main.controller.InterpreterController;

public class RunStep extends Command {
    private InterpreterController controller;

    public RunStep(String key, String description, InterpreterController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.executeOneStepForAllPrograms(controller.getRepository().getProgramStates());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
