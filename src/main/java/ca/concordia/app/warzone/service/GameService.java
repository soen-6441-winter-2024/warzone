package ca.concordia.app.warzone.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ca.concordia.app.warzone.logging.LoggingService;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.MapFile;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import ca.concordia.app.warzone.service.phase.Phase;

@Service
/**
 * Game service to load and save game progress
 */
public class GameService {
    private final PlayerService d_playerService;
    private final CountryService d_countryService;
    private final MapService d_mapService;
    private final PhaseRepository d_phaseRepository;
    /**
     * Game service constructor
     * @param p_playerService player service
     * @param p_countryService country service
     * @param p_mapService map service
     * @param p_phaseRepository phase repository
     */
    public GameService(
            PlayerService p_playerService,
            CountryService p_countryService,
            MapService p_mapService,
            PhaseRepository p_phaseRepository) {
        this.d_playerService = p_playerService;
        this.d_countryService = p_countryService;
        this.d_mapService = p_mapService;
        this.d_phaseRepository = p_phaseRepository;
    }

    /**
     * saves the current game to the specified file name
     * 
     * @param p_gameFileName file name of the game to be saved
     */
    public String saveGame(String p_gameFileName) {
        if (this.d_mapService.getMapName() == null) return "Failed to save game. Kindly save the game map.";
        String directoryPath = "game_files";

        File directory = new File(directoryPath);

        if (!directory.exists())
            directory.mkdirs();

        String l_fileName = p_gameFileName + ".game";
        File l_map = new File(directory, l_fileName);
        FileWriter l_fileWriter;

        try {
            l_fileWriter = new FileWriter(l_map, false);
            String l_mapName = this.d_mapService.getMapName();

            StringBuilder l_mapData = new StringBuilder();
            l_mapData.append("[map]");
            l_mapData.append("\n");
            l_mapData.append(l_mapName);
            l_mapData.append("\n");
            l_fileWriter.write(l_mapData.toString());

            String l_phaseName = this.d_phaseRepository.getPhase().getClass().getSimpleName();
            StringBuilder l_phaseData = new StringBuilder();
            l_phaseData.append("\n[phase]");
            l_phaseData.append("\n");
            l_phaseData.append(l_phaseName);
            l_phaseData.append("\n");
            l_fileWriter.write(l_phaseData.toString());

            StringBuilder l_countryData = new StringBuilder();
            l_countryData.append("\n[countries]");
            List<Country> countries = this.d_countryService.findAll();
            for (Country l_country : countries) {
                l_countryData.append("\n" + l_country.getId() + " " + l_country.getContinent().getId()
                        + " " + l_country.getArmiesCount());
            }
            l_countryData.append("\n");
            l_fileWriter.write(l_countryData.toString());

            StringBuilder l_neighborData = new StringBuilder();
            l_neighborData.append("\n[borders]");
            for (Country l_country : countries) {
                l_neighborData.append("\n"+ l_country.getId() + " ");
               for (Country l_neighbor : l_country.getNeighbors()) {
                    l_neighborData.append(l_neighbor.getId() + " ");
               }
    
            }
            l_neighborData.append("\n");
            l_fileWriter.write(l_neighborData.toString());


            List<Player> l_playerList = this.d_playerService.getAllPlayers();
            StringBuilder l_playerData = new StringBuilder();
            l_playerData.append("\n");
            l_playerData.append("[players]");
            l_playerData.append("\n");

            for (Player l_player : l_playerList) {
                l_playerData.append(l_player.getId() + " " + "strategyType" + " ");

                for (String card : l_player.getCards()) {
                    l_playerData.append(card + "");
                }

                l_playerData.append("\n");
            }
            l_playerData.append("\n");
            l_fileWriter.write(l_playerData.toString());

            StringBuilder l_ownershipData = new StringBuilder();
            l_ownershipData.append("[ownership]");
            l_ownershipData.append("\n");
            for (Player player : l_playerList) {
                l_ownershipData.append(player.getId() + " ");
                for (Country country : player.getCountriesAssigned()) {
                    l_ownershipData.append(country.getId() + " ");
                }
                l_ownershipData.append("\n");
            }
            l_ownershipData.append("\n");
            l_fileWriter.write(l_ownershipData.toString());

            l_fileWriter.close();
            LoggingService.log("Game saved Successfully");
            return "Game saved Successfully";

        } catch (IOException e) {
            LoggingService.log(e.getMessage());
            return e.getMessage();
        }
    }
}
