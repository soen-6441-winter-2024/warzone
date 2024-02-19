package ca.concordia.app.warzone.controller;

/**
 * Represents the phases of the game.
 */
public enum Phase {
    /**
     * The map editing phase.
     */
    MAP_EDITOR,

    /**
     * The game play phase.
     */
    GAME_PLAY,

    /**
     * The startup phase.
     */
    STARTUP,

    /**
     * The game loop phase.
     */
    GAME_LOOP
}