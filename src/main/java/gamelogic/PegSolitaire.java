package gamelogic;

import controller.Board;

import java.util.ArrayList;

import static gamelogic.Tile.*;

public class PegSolitaire {
    private final Tile[][] board;

    public PegSolitaire(Tile[][] initialState) {
        this.board = initialState;
    }

    /**
     * Initialize game using strings
     * notation:
     * ' ': Outside board
     * 'O': Marble
     * '.': Empty place
     *
     * @param initialState
     */
    public PegSolitaire(String[] initialState) {
        board = new Tile[initialState.length][initialState[0].length()];
        for (int r = 0; r < initialState.length; r++) {
            for (int c = 0; c < initialState[r].length(); c++) {
                switch (initialState[r].charAt(c)) {
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

    public int getBoardWidth() {
        return board[0].length;
    }

    public int getBoardHeight() {
        return board.length;
    }

    public Tile getTile(BoardPosition position) throws InvalidPositionException {
        if (position.getRow() >= board.length || position.getRow() < 0) {
            throw new InvalidPositionException("The given position is invalid: Row = " + position.getRow());
        }
        if (position.getColumn() >= board[0].length || position.getColumn() < 0) {
            throw new InvalidPositionException("The given position is invalid: Column = " + position.getColumn());
        }
        return board[position.getRow()][position.getColumn()];
    }

    public ArrayList<BoardPosition> getValidSteps(BoardPosition position) throws InvalidPositionException {
        Tile start = getTile(position);

        ArrayList<BoardPosition> validSteps = new ArrayList<BoardPosition>();

        if (start == OUTSIDE_BOARD) {
            throw new InvalidPositionException("The position is outside the board: Row = " + position.getRow() + ", Column = " + position.getColumn());
        }

        if (start != MARBLE) {
            return validSteps;
        }

        Integer[][] relativePositionsToCheck = {{0, 2, 0, 1}, {0, -2, 0, -1}, {2, 0, 1, 0}, {-2, 0, -1, 0}};
        for (Integer[] posToCheck : relativePositionsToCheck) {
            try {
                if (getTile(new BoardPosition(position.getRow() + posToCheck[0], position.getColumn() + posToCheck[1])) != EMPTY) {
                    continue;
                }
                if (getTile(new BoardPosition(position.getRow() + posToCheck[2], position.getColumn() + posToCheck[3])) != MARBLE) {
                    continue;
                }
            } catch (InvalidPositionException e) {
                continue;
            }
            validSteps.add(new BoardPosition(position.getRow() + posToCheck[0], position.getColumn() + posToCheck[1]));
        }

        return (validSteps);
    }

    public void setTile (BoardPosition position, Tile tile){
        board[position.getRow()][position.getColumn()] = tile;
    }

    public void performStep(BoardPosition from, BoardPosition to) throws InvalidPositionException, InvalidStepException {
        getTile(to);
        ArrayList<BoardPosition> validSteps = getValidSteps(from);
        if (!validSteps.contains(to)) {
            throw new InvalidStepException("Invalid step: from = (" + from.getRow() + ", " + from.getColumn() + "), to = (" + to.getRow() + ", " + to.getColumn() + ")");
        }

        BoardPosition middle = new BoardPosition(
                (from.getRow() + to.getRow()) / 2,
                (from.getColumn() + to.getColumn()) / 2
        );

        setTile (from, EMPTY);
        setTile (to, MARBLE);
        setTile (middle, EMPTY);
    }
}
