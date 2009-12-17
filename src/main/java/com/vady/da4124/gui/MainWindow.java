package com.vady.da4124.gui;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

import com.vady.da4124.paint.Scene;


public class MainWindow extends JFrame {

public static Logger logger = Logger.getLogger(MainWindow.class);

    Scene scene;

    private MainWindow() {
        
    }

    public MainWindow(Scene scene) {
        initGuiElements();

        this.scene = scene;
    }

    private void initGuiElements() {
        //this.setJMenuBar(new MainMenu(this));

        this.setTitle("Assignment 2 [DA4124]");

        this.setSize(800, 600);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // center
        this.setVisible(true);
    }

    public void paint(Graphics graphics) {
        scene.draw(graphics);
    }
}
