package main.model.command;

public class Exit extends Command {
    public Exit(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
