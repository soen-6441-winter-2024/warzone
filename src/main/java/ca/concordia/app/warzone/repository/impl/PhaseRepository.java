package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.controller.Phase;
import org.springframework.stereotype.Repository;


/**
 * A repository class for managing the current phase of the game.
 */
@Repository
public class PhaseRepository {
    /**
     * The current phase of the game.
     */
    private Phase phase;

    /**
     * Constructs a PhaseRepository with the initial phase set to MAP_EDITOR.
     */
    public PhaseRepository() {
        this.phase = Phase.MAP_EDITOR;
    }

    /**
     * Retrieves the current phase of the game.
     *
     * @return the current phase
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * Sets the current phase of the game.
     *
     * @param phase the phase to set
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}

