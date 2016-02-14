package com.onefactor.config;

import org.apache.commons.cli.*;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 0:17
 */
public enum SettingsFactory {
    INSTANCE;

    private static final String PORT = "port";
    private static final String PLACEMARKS_FILENAME = "placemarks";
    private static final String MAP_CELLS_FILENAME = "mapcells";

    private Settings settings;

    public boolean buildSettings(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("help")
                .desc("show this message")
                .build());
        options.addOption("p", PORT, true, "TCP port to listen");
        options.addOption(Option.builder().longOpt(PLACEMARKS_FILENAME)
                .desc("path to placemarks CSV file")
                .hasArg()
                .build());
        options.addOption(Option.builder().longOpt(MAP_CELLS_FILENAME)
                .desc("path to map cells CSV file")
                .hasArg()
                .build());

        CommandLineParser parser = new DefaultParser();
        try {
            int port = 8080;
            String placemarksFilename = "placemarks.csv";
            String mapCellsFilename = "mapcells.csv";

            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                showHelp(options);
                return false;
            }

            if (cmd.hasOption("p")) {
                port = Integer.valueOf(cmd.getOptionValue("p"));
            }

            if (cmd.hasOption(PLACEMARKS_FILENAME)) {
                placemarksFilename = cmd.getOptionValue(PLACEMARKS_FILENAME);
            }

            if (cmd.hasOption(MAP_CELLS_FILENAME)) {
                mapCellsFilename = cmd.getOptionValue(MAP_CELLS_FILENAME);
            }

            settings = new Settings(port, placemarksFilename, mapCellsFilename);

            return true;
        } catch (ParseException e) {
            System.err.println("Error while parsing command line options");
            showHelp(options);
            return false;
        }
    }

    public Settings getSettings() {
        return settings;
    }

    private static void showHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("onefactortask", options );
    }
}
