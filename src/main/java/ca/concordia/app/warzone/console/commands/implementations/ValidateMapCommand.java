package ca.concordia.app.warzone.console.commands.implementations;

import ca.concordia.app.warzone.console.commands.Command;
import ca.concordia.app.warzone.console.commands.CommandType;
import ca.concordia.app.warzone.controller.MapValidatorController;
import org.springframework.stereotype.Component;

/**
 * Represents a command to validate the map.
 */
@Component
public class ValidateMapCommand extends Command {

        /** The controller for map editing. */
        private final MapValidatorController d_MapValidatorController;

        /**
         * Constructs a ValidateMapObject object.
         *
         * @param p_mapValidatorController The controller for map editing.
         */
        public ValidateMapCommand(MapValidatorController p_mapValidatorController) {
            this.d_MapValidatorController = p_mapValidatorController;
            init();
        }
    
        /**
         * Initializes the command type.
         */
        private void init() {
            this.d_Type = CommandType.VALIDATE_MAP;
        }
    
        /**
         * Runs the validate map command.
         *
         * @param p_options The options for validating the map.
         * @return The result of the command execution.
         */
        @Override
        public String run(String[] p_options) {
            return d_MapValidatorController.validateMap();
        }


}
