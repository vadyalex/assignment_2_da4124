package com.vady.da4124.gui;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.vady.da4124.paint.Scene;


public class MainMenu extends JMenuBar implements ActionListener {

    private static final Logger LOGGER = Logger.getLogger(MainMenu.class);

    private static final String MENU_EDIT = "Edit";
    private static final String INC_Y_STEP = "Inc Y step";
    private static final String DEC_Y_STEP = "Dec Y step";
    private static final String INC_X_STEP = "Inc X step";
    private static final String DEC_X_STEP = "Dec X step";
    private static final String MENU_HELP = "Help";
    private static final String ABOUT = "About";
    private static final String MENU_FILE = "File";
    private static final String OPEN = "Open";
    private static final String EXIT = "Exit";


    public MainMenu() {
        init();
    }

    protected void init() {

        this.add(fileMenu());
        this.add(editMenu());
        this.add(helpMenu());

        this.setVisible(true);
    }

    private JMenu fileMenu() {
        JMenu menu = new JMenu(MENU_FILE);

        constructMenu(menu, new String[]{OPEN, EXIT});

        return menu;
    }


    private JMenu editMenu() {
        JMenu menu = new JMenu(MENU_EDIT);

        constructMenu(menu, new String[]{INC_Y_STEP, DEC_Y_STEP, INC_X_STEP, DEC_X_STEP});

        return menu;
    }

    private JMenu helpMenu() {
        JMenu menu = new JMenu(MENU_HELP);

        constructMenu(menu, new String[]{ABOUT});

        return menu;
    }

    protected void constructMenu(JMenu menu, String[] names) {
        for (String name : names) {
            if (name.equals("-")) {
                menu.addSeparator();
            } else {
                menu.add(menuItem(name, name));
            }
        }
    }

    protected JMenuItem menuItem(String name, String text) {
        JMenuItem result = new JMenuItem();

        result.setName(name);
        result.setText(text);

        result.addActionListener(this);

        return result;
    }

    public void actionPerformed(ActionEvent actionEvent) {
         String event = ((JMenuItem) actionEvent.getSource()).getName();

        LOGGER.info("EVENT: " + event);

        if (event == EXIT) {
            System.exit(0);
        }

        if (event == OPEN) {
            Scene.instance.selectFile();
        }

        if (event == INC_X_STEP) {
            Scene.instance.changeX(5);
        }

        if (event == DEC_X_STEP) {
            Scene.instance.changeX(-5);
        }

        if (event == INC_Y_STEP) {
            Scene.instance.changeY(5);
        }

        if (event == DEC_Y_STEP) {
            Scene.instance.changeY(-5);
        }

        if (event == ABOUT) {
            JOptionPane.showMessageDialog(null,
                    "Assignment 2 [DA4124]\n Tree Graph drawing\n  Made by:\n  Vladyslav Aleksakhin\n  (c) 2009\n  Version: 1.0",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Scene.instance.init();
        
        Scene.instance.repaint();

    }
}
