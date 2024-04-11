package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.controller.GameEngineController;
import ca.concordia.app.warzone.controller.MapEditorController;
import org.springframework.stereotype.Component;

/**
 * Represents a command to show the map.
 */
@Component
public class ShowMyCardsCommand extends Command {

    /** The controller for game engine. */
    private final GameEngineController d_GameEngineController;

    /**
     * Constructs a ShowMyCardsCommand object.
     *
     * @param p_GameEngineController The controller for map editing.
     */
    public ShowMyCardsCommand(GameEngineController p_GameEngineController) {
        this.d_GameEngineController = p_GameEngineController;
        init();
    }

    /**
     * Initializes the command type.
     */
    private void init() {
        this.d_Type = CommandType.SHOW_MAP;
    }

    /**
     * Runs the method to show the current issuing players special cards.
     *
     * @param p_options The options for showing the cards.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_options) {
        return d_GameEngineController.showMyCards();
    }
}
