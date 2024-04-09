package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

/**
 * Represents the 'tournament' command.
 * This command is used to initiate a tournament with specified parameters.
 */
@Component
public class TournamentCommand extends Command {

    private final GameEngineController d_gameEngineController;

    /**
     * Constructs a TournamentCommand with the specified GameEngineController.
     *
     * @param p_GameEngineController the GameEngineController to be used
     */
    public TournamentCommand(GameEngineController p_GameEngineController) {
        d_gameEngineController = p_GameEngineController;
    }

    /**
     * Executes the 'tournament' command with the specified subcommands and options.
     *
     * @param p_subCommandsAndOptions an array containing the subcommands and options
     * @return a message indicating the success or failure of the command execution
     * @throws InvalidCommandException if the number of arguments is incorrect or if the arguments are invalid
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        if(p_subCommandsAndOptions.length != 8) {
            throw new InvalidCommandException("expected 8 arguments");
        }

        String aM = p_subCommandsAndOptions[0];
        String aListMapFiles = p_subCommandsAndOptions[1];
        String aP = p_subCommandsAndOptions[2];
        String aListPlayerStrategies = p_subCommandsAndOptions[3];
        String aG = p_subCommandsAndOptions[4];
        String aNumberGamesStr = p_subCommandsAndOptions[5];
        String aD = p_subCommandsAndOptions[6];
        String aMaxNumberTurnsStr = p_subCommandsAndOptions[7];


        int numberGames = 0;
        try {
            numberGames = Integer.parseInt(aNumberGamesStr);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("number of games should be a number");
        }
        int maxNumberTurns = 0;
        try {
            maxNumberTurns = Integer.parseInt(aMaxNumberTurnsStr);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Maximum number of turns should be a number");
        }

        //return d_gameEngineController.advance(countryFrom, countryTo, armiesQuantity);
        return "";
    }
}