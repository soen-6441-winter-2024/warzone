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


@Component
public class AssignCountriesCommand extends Command {

    private final PlayerRepository playerRepository;
    private final CountryRepository countryRepository;



    public AssignCountriesCommand(PlayerRepository playerRepository, CountryRepository countryRepository) throws InvalidCommandException {
        this.playerRepository = playerRepository;
        this.countryRepository = countryRepository;


        }



    @Override
    public String run(String[] subCommandsAndOptions) {
//        Saves the current map being edited

        List<Player> players = playerRepository.getAllPlayers();
        List<Country> countries = countryRepository.findAll();

        Collections.shuffle(countries);

        assignCountries(players, countries);
        System.out.println("Loading Map file: ");
        return null;
    }

    public void assignCountries(List<Player> players, List<Country> countries){
        int totalPlayers = players.size();
        int minCountriesPerPlayer = countries.size() / totalPlayers;
        int remainingCountries = countries.size() % totalPlayers;
        int i = 0;

        //Distribute the countries evenly among players
        for(Player l_player : players){
            for(int j = 0; j < remainingCountries; j++){
                l_player.addCountry(countries.get(i));
                System.out.println(l_player.get_playerName() + " was assigned "+ countries.get(i));
                i++;
            }
        }

        //distribute remaining countries
        for(int j = 0; j < remainingCountries; j++){
            Player l_player = players.get(j);
            l_player.addCountry(countries.get(i));
            System.out.println(l_player.get_playerName() + " was assigned " + countries.get(i));
            i++;

        }

    }
}
