package com.vady.da4124;

import com.vady.da4124.gui.MainWindow;
import com.vady.da4124.paint.Scene;
import org.apache.log4j.Logger;

/**
 * PROGRAM ENTRY POINT
 *
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main( String[] args ) {

        if (args.length == 0) {
            LOGGER.fatal("No graph file selected..");
            LOGGER.fatal("  please add graph file name as parameter: java <..> com.vady.da4124.App someFile.graphml ]");

            return;
        }

        String graphFile = args[0];

        new MainWindow(new Scene(graphFile));
    }
}
