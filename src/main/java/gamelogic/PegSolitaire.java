package gamelogic;

import controller.BoardPosition;
import controller.InvalidPositionException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static gamelogic.Tile.*;

public class PegSolitaire {
    private Tile[][] board;

    public PegSolitaire(Tile[][] initialState) {
        this.board = initialState;
    }

    /**
     * Initialize game using strings
     * notation:
     * ' ': Outside board
     * 'O': Marble
     * '.': Empty place
     * @param initialState
     */
    public PegSolitaire(String[] initialState) {
        board = new Tile[initialState.length][initialState[0].length()];
        for (int r = 0; r < initialState.length; r++) {
            for (int c = 0; c < initialState[r].length(); c++){
                switch (initialState[r].charAt(c)){
                    case ' ':
                        board[r][c] = OUTSIDE_BOARD;
                        break;
                    case 'O':
                        board[r][c] = MARBLE;
                        break;
                    case '.':
                        board[r][c] = EMPTY;
                        break;
                }
            }
        }
    }

    public int getBoardWidth(){
        return board[0].length;
    }

    public int getBoardHeight(){
        return board.length;
    }

    public Tile getTile(Integer row, Integer column) throws InvalidPositionException{
        if (row >= board.length || row < 0){
            throw new InvalidPositionException("The given position is invalid: Row = " + row);
        }
        if (column >= board[0].length || column < 0){
            throw new InvalidPositionException("The given position is invalid: Column = " + column);
        }
        return board[row][column];
    }

    public ArrayList<BoardPosition> getValidSteps(Integer row, Integer column)  throws InvalidPositionException{
        Tile start = getTile(row, column);

        ArrayList<BoardPosition> validSteps = new ArrayList<BoardPosition>();

        if (start != MARBLE) {
            return validSteps;
        }

        Integer[][] positionsToCheck= {{0, 2, 0, 1}, {0, -2, 0, -1}, {2, 0, 1, 0}, {-2, 0, -1, 0}};
        for (Integer[] posToCheck: positionsToCheck){
            try{
                if (getTile(row + posToCheck[0], column + posToCheck[1]) != EMPTY) {
                    continue;
                }
                if (getTile(row + posToCheck[2], column + posToCheck[3]) != MARBLE) {
                    continue;
                }
            } catch (InvalidPositionException e){
                continue;
            }
            validSteps.add(new BoardPosition(row + posToCheck[0], column + posToCheck[1]));
        }

        return (validSteps);
    }
}
