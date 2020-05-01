package gamelogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static gamelogic.Tile.*;

public class PegSolitaireTest {
    @Test
    public void init() {
        Tile[][] minimalBoard = {
                {MARBLE, MARBLE, MARBLE},
                {MARBLE, MARBLE, MARBLE}
        };

        PegSolitaire solitaire = new PegSolitaire(minimalBoard);
        assertEquals(3, solitaire.getBoardWidth());
        assertEquals(2, solitaire.getBoardHeight());
    }
}
