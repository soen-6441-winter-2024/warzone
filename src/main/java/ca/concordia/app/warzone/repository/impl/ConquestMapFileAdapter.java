package ca.concordia.app.warzone.repository.impl;

import ca.concordia.app.warzone.model.MapFile;

import java.util.List;

/**
 * Adapter class for Conquest map formatter
 */
public class ConquestMapFileAdapter extends DefaultMapFileFormatter{

    private final ConquestMapFileFormatter conquestMapFileFormatter;

    /**
     * Constructor for adapter, receives the conquest file formatter
     *
     */
    public ConquestMapFileAdapter() {
        this.conquestMapFileFormatter = new ConquestMapFileFormatter();
    }

    @Override
    public String mapToString(MapFile p_mapFile) {
        return conquestMapFileFormatter.mapToString(p_mapFile);
    }

    @Override
    public MapFile stringToMap(List<String> p_lines) {
        return conquestMapFileFormatter.stringToMap(p_lines);
    }

    /**
     * Validate lines of the file based on a conquest map format
     *
     * @param mapLines lines of the file
     * @return true if valid, false otherwise
     */
    public boolean validate(List<String> mapLines) {
        return conquestMapFileFormatter.validate(mapLines);
    }
}
