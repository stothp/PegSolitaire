package main;

import gamelogic.BoardPosition;
import gamelogic.PegSolitaire;
import javafx.application.Application;

public class Main {
    private static String[] englishBoardDef = {
            "  OOO  ",
            "  OOO  ",
            "OOOOOOO",
            "OOO.OOO",
            "OOOOOOO",
            "  OOO  ",
            "  OOO  "
    };

    private static String[] test1def = {
            "  ...  ",
            "  ...  ",
            ".......",
            "....OO.",
            ".......",
            "  ...  ",
            "  ...  "
    };

    public static void main(String[] args){
        Application.launch(PegSolitaireApp.class, args);
    }
}
