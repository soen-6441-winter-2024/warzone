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
     * @param conquestMapFileFormatter map file formatter
     */
    public ConquestMapFileAdapter(ConquestMapFileFormatter conquestMapFileFormatter) {
        this.conquestMapFileFormatter = conquestMapFileFormatter;
    }

    @Override
    public String mapToString(MapFile p_mapFile) {
        return conquestMapFileFormatter.mapToString(p_mapFile);
    }

    @Override
    public MapFile stringToMap(List<String> p_lines) {
        return conquestMapFileFormatter.stringToMap(p_lines);
    }
}
