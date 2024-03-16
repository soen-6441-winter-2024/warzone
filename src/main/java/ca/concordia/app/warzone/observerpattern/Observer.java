package ca.concordia.app.warzone.observerpattern;

/**
 * The Observer interface represents an object that observes changes in the state of an Observable object.
 */
public interface Observer {

    /**
     * This method is called by the Observable object whenever its state changes.
     *
     * @param p_Observable the Observable object that changed state
     * @param p_Arg        an argument passed by the Observable (optional)
     */
    void update(Observable p_Observable, Object p_Arg);
}
