package com.swagteam360.dungeonadventure;

import com.swagteam360.dungeonadventure.view.StartMenuView;
import javafx.application.Application;

/**
 * The DungeonAdventure class serves as the entry point for launching the Dungeon
 * Adventure application. This class initializes the JavaFX application by delegating
 * the launch process to the StartMenuView class.
 * <p>
 * The primary purpose of this class is to configure and start the JavaFX runtime,
 * allowing the graphical user interface for the Dungeon Adventure game to be displayed.
 * <p>
 * It uses the JavaFX Application class's static launch method to trigger the
 * application lifecycle.
 *
 * @author Jonathan Hernandez
 * @version 1.0 (May 11, 2025)
 */
public class DungeonAdventure {

    /**
     * The main method acts as the entry point for the Dungeon Adventure application.
     * It initializes the JavaFX runtime and launches the application by delegating
     * to the {@code StartMenuView} class.
     *
     * @param theArgs Command-line arguments passed to the application during startup.
     *                These arguments are forwarded to the JavaFX runtime.
     */
    public static void main(final String[] theArgs) {
        Application.launch(StartMenuView.class, theArgs);
    }
}
