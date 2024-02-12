package ca.concordia.app.warzone.console.commands;

public abstract class SubCommand {
    protected SubCommandType type;
    protected String[] options;

    abstract public void run();
}
