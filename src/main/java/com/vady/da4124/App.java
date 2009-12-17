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

        System.setProperty("apple.laf.useScreenMenuBar", "true");  // for MacOS X only

        Scene.instance.setMainWindow(new MainWindow());
    }
}
