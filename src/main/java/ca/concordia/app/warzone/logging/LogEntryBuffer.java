package ca.concordia.app.warzone.logging;

import ca.concordia.app.warzone.observerpattern.Observable;
import ca.concordia.app.warzone.observerpattern.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The LogEntryBuffer class represents a buffer for storing log entries.
 * It acts as an Observable and notifies Observers about changes in its state.
 */
public class LogEntryBuffer extends Observable {

    private List<Observer> d_Observers;
    private List<String> d_Entries;

    /**
     * Constructs a new LogEntryBuffer with an empty list of entries.
     */
    public LogEntryBuffer() {
        d_Observers = new ArrayList<>();
        d_Entries = new ArrayList<>();
    }

    /**
     * Adds a new entry to the buffer and notifies observers about the change.
     *
     * @param p_Entry the log entry to be added
     */
    public void addEntry(String p_Entry) {
        d_Entries.add(p_Entry);
        setChanged();
        notifyObservers(p_Entry);
    }

    /**
     * Returns the list of entries stored in the buffer.
     *
     * @return the list of log entries
     */
    public List<String> getEntries() {
        return d_Entries;
    }
}
