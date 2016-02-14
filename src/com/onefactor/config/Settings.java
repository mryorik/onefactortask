package com.onefactor.config;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 0:25
 */
public class Settings {
    private final int port;
    private final String placemarksFilename;
    private final String mapCellsFilename;

    public Settings(int port, String placemarksFilename, String mapCellsFilename) {
        this.port = port;
        this.placemarksFilename = placemarksFilename;
        this.mapCellsFilename = mapCellsFilename;
    }

    public int getPort() {
        return port;
    }

    public String getPlacemarksFilename() {
        return placemarksFilename;
    }

    public String getMapCellsFilename() {
        return mapCellsFilename;
    }
}
