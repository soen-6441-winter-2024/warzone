package ca.concordia.app.warzone.console.commands.implementations.subcommands;

import ca.concordia.app.warzone.console.commands.SubCommand;
import ca.concordia.app.warzone.console.commands.SubCommandType;
import ca.concordia.app.warzone.console.dto.CountryDto;
import ca.concordia.app.warzone.console.exceptions.InvalidCommandException;
import ca.concordia.app.warzone.controller.MapEditorController;

/**
 * Represents a sub-command for removing a country from a map in a map editor.
 */
public class RemoveCountrySubCommand extends SubCommand {

    private final MapEditorController d_controller;

    /**
     * Constructs a RemoveCountrySubCommand with the specified options and map editor controller.
     *
     * @param options    The options for removing a country. Expected length is 1.
     * @param p_controller The map editor controller to use for removing the country.
     * @throws InvalidCommandException if the options length is not 1.
     */
    public RemoveCountrySubCommand(String[] options, MapEditorController p_controller) throws InvalidCommandException {
        this.d_Type = SubCommandType.REMOVE;
        if (options.length != 1) {
            throw new InvalidCommandException("invalid options length, expected 1");
        }

        this.d_Options = options;
        this.d_controller = p_controller;
    }

    /**
     * Runs the RemoveCountrySubCommand by removing a country from a map using the map editor controller.
     *
     * @return A string message indicating the result of the operation.
     */
    @Override
    public String run() {
        // Call RemoveCountry from the service class
        CountryDto dto = new CountryDto();
        dto.setId(this.d_Options[0]);
        System.out.println("Removing a country with Id: " + dto.getId());
        return d_controller.deleteCountry(dto.getId());
    }
}

