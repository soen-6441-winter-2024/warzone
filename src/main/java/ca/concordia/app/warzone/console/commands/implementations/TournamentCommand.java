package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.GameEngineController;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String[] mapFilenames = this.getMapFilenames(p_subCommandsAndOptions);
        String[] playerStrategies = this.getPlayerStrategies(p_subCommandsAndOptions);
        int maxTurns = this.getMaxTurns(p_subCommandsAndOptions);
        int gamesAmount = this.getGamesAmount(p_subCommandsAndOptions);



        return "";
    }

    private int getMaxTurns(String[] p_subCommandsAndOptions) {
        Pattern pattern = Pattern.compile("(?<=-D) ((((\\S+))\\s)+)");
        Matcher matcher = pattern.matcher(String.join(" ", p_subCommandsAndOptions));

        if(!matcher.find()) {
            throw new InvalidCommandException("must specify a max turn number");
        }

        String gameAmountStr = matcher.group().trim();
        try {
            int maxTurnNumber = Integer.parseInt(gameAmountStr);
            if(maxTurnNumber < 10 || maxTurnNumber > 50) {
                throw new InvalidCommandException("max turn number must be between 10 and 50");
            }

            return maxTurnNumber;
        } catch (NumberFormatException e){
            throw new InvalidCommandException("invalid max number of turns passed");
        }
    }

    private int getGamesAmount(String[] p_subCommandsAndOptions) {
        Pattern pattern = Pattern.compile("(?<=-G) ((((\\S+))\\s)+)(?=-D)");
        Matcher matcher = pattern.matcher(String.join(" ", p_subCommandsAndOptions));

        if(!matcher.find()) {
            throw new InvalidCommandException("must specify a game amount");
        }

        String gameAmountStr = matcher.group().trim();
        try {
            int gameAmount = Integer.parseInt(gameAmountStr);
            if(gameAmount < 1 || gameAmount > 5) {
                throw new InvalidCommandException("game amount number must be between 1 and 5");
            }

            return gameAmount;
        } catch (NumberFormatException e){
            throw new InvalidCommandException("invalid game amount passed");
        }
    }

    private String[] getPlayerStrategies(String[] p_subCommandsAndOptions) {
        Pattern pattern = Pattern.compile("(?<=-P) ((((\\S+))\\s)+)(?=-G)");
        Matcher matcher = pattern.matcher(String.join(" ", p_subCommandsAndOptions));

        if(!matcher.find()) {
            throw new InvalidCommandException("must specify at least 2 player strategies");
        }

        String[] strategies = matcher.group().trim().split(" ");
        if(strategies.length > 4) {
            throw new InvalidCommandException("strategies quantity must be between 2 and 4");
        }

        return strategies;
    }

    private String[] getMapFilenames(String[] p_subCommandsAndOptions) {
        Pattern mapFilesPattern = Pattern.compile("(?<=-M) ((((\\S+))\\s)+)(?=-P)");
        Matcher matcher = mapFilesPattern.matcher(String.join(" ", p_subCommandsAndOptions));

        if(!matcher.find()) {
            throw new InvalidCommandException("must specify file names");
        }

        String[] mapFileNames = matcher.group().trim().split(" ");
        if(mapFileNames.length > 5) {
            throw new InvalidCommandException("map files quantity must be between 1 and 5");
        }

        return mapFileNames;
    }
}