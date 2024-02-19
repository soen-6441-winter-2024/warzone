package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.controller.Phase;
import org.springframework.stereotype.Repository;

@Repository
public class PhaseRepository {
    public PhaseRepository() {
        this.phase = Phase.MAP_EDITOR;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    private Phase phase;
}
