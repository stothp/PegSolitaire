package controller;

import com.sun.nio.sctp.PeerAddressChangeNotification;
import gamelogic.BoardPosition;
import gamelogic.InvalidPositionException;
import gamelogic.PegSolitaire;
import gamelogic.Tile;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private PegSolitaire solitaire;
    private BoardController board;

    private static final String[] englishBoardDef = {
            "  OOO  ",
            "  OOO  ",
            "OOOOOOO",
            "OOO.OOO",
            "OOOOOOO",
            "  OOO  ",
            "  OOO  "
    };

    @FXML
    private VBox vbox1;

    @FXML
    private Pane pane1;

    public GameController() {
    }

    @FXML
    public void initialize(){
        solitaire = new PegSolitaire(englishBoardDef);
        //mainContainer.getChildren().add(new Label("Heló"));
        //vbox1.setVgrow(pane1, Priority.ALWAYS);
        this.board = new BoardController(solitaire.getRowsCount(), solitaire.getColumsCount());
        pane1.getChildren().add(board);
        pane1.autosize();
        refreshBoard();
        this.board.addEventHandler(TileClickedEvent.TILE_CLICKED_EVENT_TYPE, new EventHandler<TileClickedEvent>() {
            @Override
            public void handle(TileClickedEvent event) {
                try {
                    ArrayList<BoardPosition> validsteps = solitaire.getValidSteps(new BoardPosition(event.getRow(), event.getColumn()));
                    if (validsteps.size() > 0) {
                        board.setTileState(event.getRow(), event.getColumn(), TileController.State.SELECTED);
                    }
                } catch (InvalidPositionException e){

                }
            }
        });
        //board.setTileState(0, 0, TileController.State.SELECTED);
    }

    private void refreshBoard() {
        for (int r = 0; r < solitaire.getRowsCount(); r++){
            for (int c = 0; c < solitaire.getColumsCount(); c++){
                try {
                    switch (solitaire.getTile(new BoardPosition(r, c))) {
                        case MARBLE:
                            board.setTileState(r, c, TileController.State.MARBLE);
                            break;
                        case EMPTY:
                            board.setTileState(r, c, TileController.State.EMPTY);
                            break;
                    }
                } catch (InvalidPositionException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void menuNewGameClicked(){
    }

}
