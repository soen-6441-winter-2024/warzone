package ca.concordia.app.warzone.service;

import ca.concordia.app.warzone.console.dto.ContinentDto;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.dto.MapDto;
import ca.concordia.app.warzone.console.dto.PlayerDto;
import ca.concordia.app.warzone.exceptions.InvalidMapContentFormat;
import ca.concordia.app.warzone.logging.LoggingService;
import ca.concordia.app.warzone.model.Continent;
import ca.concordia.app.warzone.model.Country;
import ca.concordia.app.warzone.model.Player;
import ca.concordia.app.warzone.repository.impl.PhaseRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Game service to load and save game progress
 */
@Service
public class GameService {
    private final PlayerService d_playerService;
    private final CountryService d_countryService;
    private final MapService d_mapService;
    private final PhaseRepository d_phaseRepository;
    private final ContinentService d_continentService;

    /**
     * Constructor
     * 
     * @param p_playerService    playerService
     * @param p_countryService   countryService
     * @param p_mapService       mapService
     * @param p_phaseRepository  phaseRepository
     * @param p_continentService continent service
     *
     */
    public GameService(
            PlayerService p_playerService,
            CountryService p_countryService,
            MapService p_mapService,
            PhaseRepository p_phaseRepository,
            ContinentService p_continentService) {
        this.d_playerService = p_playerService;
        this.d_countryService = p_countryService;
        this.d_mapService = p_mapService;
        this.d_phaseRepository = p_phaseRepository;
        this.d_continentService = p_continentService;
    }

    /**
     * saves the current game to the specified file name
     * 
     * @param p_gameFileName file name of the game to be saved
     * @return the result of the operation
     */
    public String saveGame(String p_gameFileName) {
        // if (this.d_mapService.getMapName() == null) return "Failed to save game.
        // Kindly save the game map.";
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

            // game map
            StringBuilder l_mapData = new StringBuilder();
            l_mapData.append("[map]");
            l_mapData.append("\n");
            l_mapData.append(l_mapName);
            l_mapData.append("\n");
            l_fileWriter.write(l_mapData.toString());

            // phase
            String l_phaseName = this.d_phaseRepository.getPhase().getClass().getSimpleName();
            StringBuilder l_phaseData = new StringBuilder();
            l_phaseData.append("\n[phase]");
            l_phaseData.append("\n");
            l_phaseData.append(l_phaseName);
            l_phaseData.append("\n");
            l_fileWriter.write(l_phaseData.toString());

            // continent
            StringBuilder l_continentData = new StringBuilder();
            l_continentData.append("[continent]");
            List<Continent> continents = this.d_continentService.findAll();
            for (Continent continent : continents) {
                l_continentData.append("\n" + continent.getId() + " " + continent.getSizeOfContinent());
            }
            l_continentData.append("\n");
            l_fileWriter.write(l_continentData.toString());

            // country
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
                l_neighborData.append("\n" + l_country.getId() + " ");
                for (Country l_neighbor : l_country.getNeighbors()) {
                    l_neighborData.append(l_neighbor.getId() + " ");
                }

            }
            l_neighborData.append("\n");
            l_fileWriter.write(l_neighborData.toString());

            // players
            List<Player> l_playerList = this.d_playerService.getAllPlayers();
            StringBuilder l_playerData = new StringBuilder();
            l_playerData.append("\n");
            l_playerData.append("[players]");
            l_playerData.append("\n");

            for (Player l_player : l_playerList) {
                l_playerData.append(l_player.getId() + " ");

                for (String card : l_player.getCards()) {
                    l_playerData.append(card + " ");
                }

                l_playerData.append("\n");
            }
            l_playerData.append("\n");
            l_fileWriter.write(l_playerData.toString());

            // country ownership
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

    /**
     * Loads a game file
     * 
     * @param p_gameFileName name of the game file to load
     * @return A text describing the status of loading the game file.
     */
    public String loadGame(String p_gameFileName) {
        String directoryPath = "game_files";
        File directory = new File(directoryPath);

        String l_fileName = p_gameFileName + ".game";
        File l_gameFile = new File(directory, l_fileName);

        if (!l_gameFile.exists()) {
            LoggingService.log("Game file " + p_gameFileName + " not found.");
            return "Game file " + p_gameFileName + " not found.";
        }

        try (Scanner scanner = new Scanner(l_gameFile)) {
            while (scanner.hasNextLine()) {
                String l_stringLine = scanner.nextLine();

                switch (l_stringLine) {
                    case "[map]":
                        MapDto l_mapDto = new MapDto();
                        String fileName = scanner.nextLine();
                        if (fileName.equals(null))
                            throw new InvalidMapContentFormat("Invalid map: Game map not found.");
                        l_mapDto.setFileName(fileName);
                        this.d_mapService.loadMap(l_mapDto);
                        break;
                    case "[countries]":
                        while (scanner.hasNextLine()) {
                            String l_line = scanner.nextLine();

                            if (l_line.equals(""))
                                break;

                            String[] l_parts = l_line.split(" ");
                            String l_countryId = l_parts[0];
                            String l_continentId = l_parts[1];
                            int l_numOfArmies = Integer.parseInt(l_parts[2]);

                            Optional<ContinentDto> continent = this.d_continentService.findById(l_continentId);

                            if (continent.isEmpty())
                                throw new InvalidMapContentFormat("Invalid map: Continent not found.");

                            CountryDto countryDto = new CountryDto();
                            countryDto.setContinent(continent.get());
                            countryDto.setId(l_countryId);
                            countryDto.setNumberOfArmies(l_numOfArmies);

                            this.d_countryService.add(countryDto);
                        }
                        break;
                    case "[continents]":
                        while (scanner.hasNextLine()) {
                            String l_line = scanner.nextLine();
                            if (l_line.equals(""))
                                break;

                            String[] l_parts = l_line.split(" ");
                            String l_continentId = l_parts[0];
                            String l_continentValue = l_parts[1];

                            ContinentDto continentDto = new ContinentDto();
                            continentDto.setId(l_continentId);
                            continentDto.setValue(l_continentValue);

                            this.d_continentService.add(continentDto);
                        }
                        break;
                    case "[players]":
                        while (scanner.hasNextLine()) {
                            String l_line = scanner.nextLine();
                            if (l_line.equals(""))
                                break;

                            String[] l_parts = l_line.split(" ");
                            String l_playerId = l_parts[0];

                            PlayerDto playerDto = new PlayerDto();
                            playerDto.setPlayerName(l_playerId);

                            // assigned cards
                            for (int i = 1; i < l_parts.length; i++) {
                                if(l_parts[i] != null) playerDto.addCardToPlayer(l_parts[i]);
                            }
                            
                            this.d_playerService.add(playerDto);
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            return e.getMessage();
            // TODO: handle exception
        }

        return null;
    }
}
