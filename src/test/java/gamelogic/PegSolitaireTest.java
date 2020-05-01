package gamelogic;

import controller.BoardPosition;
import controller.InvalidPositionException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static gamelogic.Tile.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PegSolitaireTest{
    private Tile[][] minimalBoardByArr = {
            {MARBLE, MARBLE, MARBLE},
            {MARBLE, MARBLE, MARBLE}
    };

    private String[] minimalBoardDef = {
            "OOO",
            "OOO"
    };

    private String[] englishBoardDef = {
            "  OOO  ",
            "  OOO  ",
            "OOOOOOO",
            "OOO.OOO",
            "OOOOOOO",
            "  OOO  ",
            "  OOO  "
    };

    private String[] europeanBoardDef = {
            "  OOO  ",
            " OOOOO ",
            "OOOOOOO",
            "OOO.OOO",
            "OOOOOOO",
            " OOOOO ",
            "  OOO  "
    };

    private String[] englishBoardsDef = {
            "  ...  ",
            "  ...  ",
            "...O...",
            "..OOO..",
            "...O...",
            "  ...  ",
            "  ...  "
    };


    @Test
    public void initTests() throws InvalidPositionException {
        assertEquals(3, new PegSolitaire(minimalBoardByArr).getBoardWidth());
        assertEquals(2, new PegSolitaire(minimalBoardByArr).getBoardHeight());
        assertEquals(MARBLE, new PegSolitaire(minimalBoardByArr).getTile(0, 0));

        assertEquals(3, new PegSolitaire(minimalBoardDef).getBoardWidth());
        assertEquals(2, new PegSolitaire(minimalBoardDef).getBoardHeight());
        assertEquals(MARBLE, new PegSolitaire(minimalBoardDef).getTile(1, 0));
        assertEquals(MARBLE, new PegSolitaire(minimalBoardDef).getTile(0, 2));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(-1, 0));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(0, -1));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(-1, -1));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(2, 0));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(0, 3));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(2, 3));

        assertEquals(7, new PegSolitaire(englishBoardDef).getBoardWidth());
        assertEquals(7, new PegSolitaire(englishBoardDef).getBoardHeight());
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(0, 0));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(0, 6));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(6, 0));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(6, 6));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(1, 1));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(5, 1));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(1, 5));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(5, 5));
        assertEquals(MARBLE, new PegSolitaire(englishBoardDef).getTile(0, 2));
        assertEquals(MARBLE, new PegSolitaire(englishBoardDef).getTile(2, 0));
        assertEquals(EMPTY, new PegSolitaire(englishBoardDef).getTile(3, 3));

        assertEquals(7, new PegSolitaire(europeanBoardDef).getBoardWidth());
        assertEquals(7, new PegSolitaire(europeanBoardDef).getBoardHeight());
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(0, 0));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(0, 6));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(6, 0));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(6, 6));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(1, 1));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(5, 1));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(1, 5));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(5, 5));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(0, 2));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(2, 0));
        assertEquals(EMPTY, new PegSolitaire(europeanBoardDef).getTile(3, 3));
    }

    @Test
    public void stepTests() throws InvalidPositionException {
        final PegSolitaire englishBoard1 = new PegSolitaire(englishBoardDef);
        assertThrows(InvalidPositionException.class, () -> englishBoard1.getValidSteps(-1, -1));
        assertEquals(0, englishBoard1.getValidSteps(0, 0).size());
        assertEquals(0, englishBoard1.getValidSteps(3, 3).size());
        assertTrue(englishBoard1.getValidSteps(5, 3).contains(new BoardPosition(3, 3)));
        assertTrue(englishBoard1.getValidSteps(1, 3).contains(new BoardPosition(3, 3)));
        assertTrue(englishBoard1.getValidSteps(3, 5).contains(new BoardPosition(3, 3)));
        assertTrue(englishBoard1.getValidSteps(3, 1).contains(new BoardPosition(3, 3)));
        String[] state1 = {
                "  ...  ",
                "  ...  ",
                "...O...",
                "..OOO..",
                "...O...",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board1 = new PegSolitaire(state1);
        ArrayList<BoardPosition> validSteps = board1.getValidSteps(3,3);
        assertTrue(validSteps.contains(new BoardPosition(1, 3)));
        assertTrue(validSteps.contains(new BoardPosition(5, 3)));
        assertTrue(validSteps.contains(new BoardPosition(3, 1)));
        assertTrue(validSteps.contains(new BoardPosition(3, 5)));
        assertEquals(4, board1.getValidSteps(3, 3).size());
        assertEquals(0, board1.getValidSteps(2, 3).size());
        assertEquals(0, board1.getValidSteps(4, 3).size());
        assertEquals(0, board1.getValidSteps(3, 2).size());
        assertEquals(0, board1.getValidSteps(3, 4).size());
        String[] state2 = {
                "  ...  ",
                "  ...  ",
                "......O",
                ".....OO",
                "......O",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board2 = new PegSolitaire(state2);
        assertEquals(1, board2.getValidSteps(3, 6).size());
    }
}
