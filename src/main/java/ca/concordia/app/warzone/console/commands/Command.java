package ca.concordia.app.warzone.console.commands;

import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.console.commands.SubCommand;

public abstract class Command {
    protected CommandType type;
    protected String[] options;

    protected SubCommand[] subCommands;

    abstract public void run();
}
