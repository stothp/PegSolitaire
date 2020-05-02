package gamelogic;

import org.junit.jupiter.api.Test;

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

//    private String[] englishBoardsDef = {
//            "  ...  ",
//            "  ...  ",
//            "...O...",
//            "..OOO..",
//            "...O...",
//            "  ...  ",
//            "  ...  "
//    };


    @Test
    public void initTests() throws InvalidPositionException {
        assertEquals(3, new PegSolitaire(minimalBoardByArr).getColumsCount());
        assertEquals(2, new PegSolitaire(minimalBoardByArr).getRowsCount());
        assertEquals(MARBLE, new PegSolitaire(minimalBoardByArr).getTile(new BoardPosition(0, 0)));

        assertEquals(3, new PegSolitaire(minimalBoardDef).getColumsCount());
        assertEquals(2, new PegSolitaire(minimalBoardDef).getRowsCount());
        assertEquals(MARBLE, new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(1, 0)));
        assertEquals(MARBLE, new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(0, 2)));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(-1, 0)));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(0, -1)));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(-1, -1)));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(2, 0)));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(0, 3)));
        assertThrows(InvalidPositionException.class, () -> new PegSolitaire(minimalBoardDef).getTile(new BoardPosition(2, 3)));

        assertEquals(7, new PegSolitaire(englishBoardDef).getColumsCount());
        assertEquals(7, new PegSolitaire(englishBoardDef).getRowsCount());
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(0, 0)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(0, 6)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(6, 0)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(6, 6)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(1, 1)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(5, 1)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(1, 5)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(5, 5)));
        assertEquals(MARBLE, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(0, 2)));
        assertEquals(MARBLE, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(2, 0)));
        assertEquals(EMPTY, new PegSolitaire(englishBoardDef).getTile(new BoardPosition(3, 3)));

        assertEquals(7, new PegSolitaire(europeanBoardDef).getColumsCount());
        assertEquals(7, new PegSolitaire(europeanBoardDef).getRowsCount());
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(6, 0)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(0, 6)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(6, 6)));
        assertEquals(OUTSIDE_BOARD, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(0, 0)));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(1, 1)));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(5, 1)));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(1, 5)));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(5, 5)));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(0, 2)));
        assertEquals(MARBLE, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(2, 0)));
        assertEquals(EMPTY, new PegSolitaire(europeanBoardDef).getTile(new BoardPosition(3, 3)));
    }

    @Test
    public void stepPositionTests() throws InvalidPositionException {
        final PegSolitaire englishBoard1 = new PegSolitaire(englishBoardDef);
        assertThrows(InvalidPositionException.class, () -> englishBoard1.getValidSteps(new BoardPosition(-1, -1)));
        assertThrows(InvalidPositionException.class, () -> englishBoard1.getValidSteps(new BoardPosition(0, 0)));
        assertEquals(0, englishBoard1.getValidSteps(new BoardPosition(3, 3)).size());
        assertTrue(englishBoard1.getValidSteps(new BoardPosition(5, 3)).contains(new BoardPosition(3, 3)));
        assertTrue(englishBoard1.getValidSteps(new BoardPosition(1, 3)).contains(new BoardPosition(3, 3)));
        assertTrue(englishBoard1.getValidSteps(new BoardPosition(3, 5)).contains(new BoardPosition(3, 3)));
        assertTrue(englishBoard1.getValidSteps(new BoardPosition(3, 1)).contains(new BoardPosition(3, 3)));
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
        ArrayList<BoardPosition> validSteps = board1.getValidSteps(new BoardPosition(3,3));
        assertTrue(validSteps.contains(new BoardPosition(1, 3)));
        assertTrue(validSteps.contains(new BoardPosition(5, 3)));
        assertTrue(validSteps.contains(new BoardPosition(3, 1)));
        assertTrue(validSteps.contains(new BoardPosition(3, 5)));
        assertEquals(0, board1.getValidSteps(new BoardPosition(2, 3)).size());
        assertEquals(0, board1.getValidSteps(new BoardPosition(4, 3)).size());
        assertEquals(4, board1.getValidSteps(new BoardPosition(3, 3)).size());
        assertEquals(0, board1.getValidSteps(new BoardPosition(3, 2)).size());
        assertEquals(0, board1.getValidSteps(new BoardPosition(3, 4)).size());
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
        assertEquals(1, board2.getValidSteps(new BoardPosition(3, 6)).size());
    }

    @Test
    public void performStepTests() throws InvalidStepException, InvalidPositionException{
        final PegSolitaire englishBoard1 = new PegSolitaire(englishBoardDef);
        assertThrows(InvalidPositionException.class, () -> englishBoard1.performStep(new BoardPosition(-1, -1), new BoardPosition(0, 0)));
        assertThrows(InvalidPositionException.class, () -> englishBoard1.performStep(new BoardPosition(0, 0), new BoardPosition(0, 0)));
        assertThrows(InvalidStepException.class, () -> englishBoard1.performStep(new BoardPosition(5, 3), new BoardPosition(5, 4)));
        englishBoard1.performStep(new BoardPosition(5, 3), new BoardPosition(3, 3));
        assertEquals(EMPTY, englishBoard1.getTile(new BoardPosition(5, 3)));
        assertEquals(EMPTY, englishBoard1.getTile(new BoardPosition(4, 3)));
        assertEquals(MARBLE, englishBoard1.getTile(new BoardPosition(3, 3)));

        String[] state1 = {
                "  ...  ",
                "  ...  ",
                ".......",
                ".....OO",
                ".......",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board1 = new PegSolitaire(state1);
        assertThrows(InvalidPositionException.class, () -> board1.performStep(new BoardPosition(3, 5), new BoardPosition(3, 7)));
        board1.performStep(new BoardPosition(3, 6), new BoardPosition(3, 4));
        assertEquals(EMPTY, board1.getTile(new BoardPosition(3, 6)));
        assertEquals(EMPTY, board1.getTile(new BoardPosition(3, 5)));
        assertEquals(MARBLE, board1.getTile(new BoardPosition(3, 4)));
    }

    @Test
    public void solvedTests() throws InvalidPositionException, InvalidStepException{
        final PegSolitaire englishBoard1 = new PegSolitaire(englishBoardDef);
        assertFalse(englishBoard1.isSolved());

        String[] state1 = {
                "  ...  ",
                "  ...  ",
                ".......",
                ".....OO",
                ".......",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board1 = new PegSolitaire(state1);
        assertFalse(board1.isSolved());
        board1.performStep(new BoardPosition(3, 6), new BoardPosition(3, 4));
        assertTrue(board1.isSolved());

        String[] state2 = {
                "  ...  ",
                "  ...  ",
                "......O",
                "......O",
                ".......",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board2 = new PegSolitaire(state2);
        assertFalse(board2.isSolved());
        board2.performStep(new BoardPosition(2, 6), new BoardPosition(4, 6));
        assertTrue(board2.isSolved());

        String[] state3 = {
                "  ...  ",
                "  .O.  ",
                "...O...",
                ".......",
                ".......",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board3 = new PegSolitaire(state3);
        assertFalse(board3.isSolved());
        board3.performStep(new BoardPosition(1, 3), new BoardPosition(3, 3));
        assertTrue(board3.isSolved());
    }

    @Test
    public void endedTests() {
        String[] state1 = {
                "  ...  ",
                "  ...  ",
                ".......",
                ".....OO",
                ".......",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board1 = new PegSolitaire(state1);
        assertFalse(board1.isEnded());

        String[] state2 = {
                "  ...  ",
                "  ...  ",
                ".......",
                "....O.O",
                ".......",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board2 = new PegSolitaire(state2);
        assertTrue(board2.isEnded());

        String[] state3 = {
                "  O..  ",
                "  ...  ",
                ".......",
                "....O..",
                "O......",
                "  ...  ",
                "  .O.  "
        };
        final PegSolitaire board3 = new PegSolitaire(state3);
        assertTrue(board3.isEnded());

        String[] state4 = {
                "  ...  ",
                "  ...  ",
                ".......",
                "....O..",
                ".......",
                "  ...  ",
                "  ...  "
        };
        final PegSolitaire board4 = new PegSolitaire(state4);
        assertTrue(board4.isEnded());

    }

}
