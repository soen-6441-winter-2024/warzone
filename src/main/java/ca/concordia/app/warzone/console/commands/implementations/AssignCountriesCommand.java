package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.repository.CountryRepository;
import ca.concordia.app.warzone.repository.PlayerRepository;
import ca.concordia.app.warzone.service.model.Country;
import ca.concordia.app.warzone.service.model.Player;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Command to assign countries to players in the game.
 */
@Component
public class AssignCountriesCommand extends Command {

    private final PlayerRepository d_PlayerRepository;
    private final CountryRepository d_CountryRepository;

    /**
     * Constructor for AssignCountriesCommand.
     * @param p_playerRepository The PlayerRepository instance.
     * @param p_countryRepository The CountryRepository instance.
     * @throws InvalidCommandException Throws if the command is invalid.
     */
    public AssignCountriesCommand(PlayerRepository p_playerRepository, CountryRepository p_countryRepository) throws InvalidCommandException {
        this.d_PlayerRepository = p_playerRepository;
        this.d_CountryRepository = p_countryRepository;
    }

    /**
     * Runs the assign countries command.
     * @param p_subCommandsAndOptions The array of subcommands and options.
     * @return The result of the command execution.
     */
    @Override
    public String run(String[] p_subCommandsAndOptions) {
        List<Player> players = d_PlayerRepository.getAllPlayers();
        List<Country> countries = d_CountryRepository.findAll();

        Collections.shuffle(countries);

        assignCountries(players, countries);
        System.out.println("Loading Map file: ");
        return null;
    }

    /**
     * Assigns countries to players.
     * @param p_players The list of players.
     * @param p_countries The list of countries.
     */
    public void assignCountries(List<Player> p_players, List<Country> p_countries) {
        int totalPlayers = p_players.size();
        int minCountriesPerPlayer = p_countries.size() / totalPlayers;
        int remainingCountries = p_countries.size() % totalPlayers;
        int i = 0;

        // Distribute the countries evenly among players
        for (Player player : p_players) {
            for (int j = 0; j < remainingCountries; j++) {
                player.addCountry(p_countries.get(i));
                System.out.println(player.getPlayerName() + " was assigned " + p_countries.get(i));
                i++;
            }
        }

        // Distribute remaining countries
        for (int j = 0; j < remainingCountries; j++) {
            Player player = p_players.get(j);
            player.addCountry(p_countries.get(i));
            System.out.println(player.getPlayerName() + " was assigned " + p_countries.get(i));
            i++;
        }
    }
}
