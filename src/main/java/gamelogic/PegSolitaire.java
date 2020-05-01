package gamelogic;

public class PegSolitaire {
    private Tile[][] board;

    public PegSolitaire(Tile[][] initialState) {
        this.board = initialState;
    }

    public int getBoardWidth(){
        return board[0].length;
    }

    public int getBoardHeight(){
        return board.length;
    }
}
