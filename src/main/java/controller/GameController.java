package controller;

import com.sun.nio.sctp.PeerAddressChangeNotification;
import gamelogic.*;
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

    private static final String[] testDef = {
            "  ...  ",
            "  ...  ",
            "..OOO..",
            "..OOO..",
            "..OOO..",
            "  ...  ",
            "  ...  "
    };

    private BoardPosition selection = null;
    private ArrayList<BoardPosition> validSteps = null;

    @FXML
    private VBox vbox1;

    @FXML
    private Pane pane1;

    public GameController() {
    }

    @FXML
    public void initialize(){
        solitaire = new PegSolitaire(testDef);
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
                    BoardPosition tileClicked = new BoardPosition(event.getRow(), event.getColumn());

                    if (selection == null) {
                        validSteps = solitaire.getValidSteps(tileClicked);
                        if (validSteps.size() > 0) {
                            board.setTileState(event.getRow(), event.getColumn(), TileController.State.SELECTED);
                            for (BoardPosition pos: validSteps){
                                board.setTileState(pos.getRow(), pos.getColumn(), TileController.State.SELECTABLE);
                            }
                            selection = tileClicked;
                        }
                        return;
                    }

                    if (validSteps.contains(tileClicked)){
                        solitaire.performStep(selection, tileClicked);
                        selection = null;
                        validSteps = null;
                        refreshBoard();
                        return;
                    }

                    validSteps = solitaire.getValidSteps(tileClicked);
                    if (validSteps.size() > 0) {
                        refreshBoard();
                        board.setTileState(event.getRow(), event.getColumn(), TileController.State.SELECTED);
                        for (BoardPosition pos: validSteps){
                            board.setTileState(pos.getRow(), pos.getColumn(), TileController.State.SELECTABLE);
                        }
                        selection = tileClicked;
                        return;
                    }

                    selection = null;
                    validSteps = null;
                    refreshBoard();

                } catch (InvalidPositionException e){
                } catch (InvalidStepException e){
                }
            }
        });
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
