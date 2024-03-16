package ca.concordia.app.warzone.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import ca.concordia.app.warzone.observerpattern.Observable;
import ca.concordia.app.warzone.observerpattern.Observer;

/**
 * The LogWriter class is an Observer that listens for changes in a LogEntryBuffer
 * and writes the entries to a log file.
 */
public class LogWriter implements Observer {
    private String d_LogFile;

    /**
     * Constructs a new LogWriter with the specified log file.
     *
     * @param p_LogFile the path to the log file
     */
    public LogWriter(String p_LogFile) {
        this.d_LogFile = p_LogFile;
    }

    /**
     * This method is called by the Observable object whenever its state changes.
     * It writes the entries from the LogEntryBuffer to the log file.
     *
     * @param p_Observable the Observable object that changed state (LogEntryBuffer in this case)
     * @param p_Arg        an argument passed by the Observable (not used in this implementation)
     */
    @Override
    public void update(Observable p_Observable, Object p_Arg) {
        if (p_Observable instanceof LogEntryBuffer) {
            List<String> l_Entries = ((LogEntryBuffer) p_Observable).getEntries();
            try (PrintWriter l_Writer = new PrintWriter(new FileWriter(d_LogFile, true))) {
                for (String l_Entry : l_Entries) {
                    l_Writer.println(l_Entry);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
