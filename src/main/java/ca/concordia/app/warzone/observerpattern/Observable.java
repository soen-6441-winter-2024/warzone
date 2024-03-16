package ca.concordia.app.warzone.observerpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * The Observable class represents an object that can be observed by other objects
 * (Observers) for changes in its state.
 */
public class Observable {

    private List<Observer> d_Observers;
    private boolean d_Changed;

    /**
     * Constructs a new Observable object.
     */
    public Observable() {
        d_Observers = new ArrayList<>();
        d_Changed = false;
    }

    /**
     * Adds an observer to the list of observers.
     *
     * @param p_Observer the observer to be added
     */
    public void addObserver(Observer p_Observer) {
        d_Observers.add(p_Observer);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param p_Observer the observer to be removed
     */
    public void removeObserver(Observer p_Observer) {
        d_Observers.remove(p_Observer);
    }

    /**
     * Notifies all registered observers about a change in the observable object's state.
     *
     * @param p_Arg the argument to be passed to the observers
     */
    public void notifyObservers(Object p_Arg) {
        if (d_Changed) {
            for (Observer observer : d_Observers) {
                observer.update(this, p_Arg);
            }
            d_Changed = false;
        }
    }

    /**
     * Marks the Observable as having been changed.
     * This method should be called whenever the state of the Observable changes.
     */
    protected void setChanged() {
        d_Changed = true;
    }
}
