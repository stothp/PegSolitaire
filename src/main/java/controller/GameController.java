package controller;

import gamelogic.BoardPosition;
import gamelogic.InvalidPositionException;
import gamelogic.InvalidStepException;
import gamelogic.PegSolitaire;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GameController {
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
            "..O....",
            "..O....",
            ".......",
            "  ...  ",
            "  ...  "
    };
    private PegSolitaire solitaire;
    private BoardController board;
    private BoardPosition selection = null;
    private ArrayList<BoardPosition> validSteps = null;

    @FXML
    private VBox vbox1;

    @FXML
    private Pane pane1;

    public GameController() {
    }

    @FXML
    public void initialize() {
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
                    BoardPosition tileClicked = new BoardPosition(event.getRow(), event.getColumn());

                    if (selection == null) {
                        validSteps = solitaire.getValidSteps(tileClicked);
                        if (validSteps.size() > 0) {
                            board.setTileState(event.getRow(), event.getColumn(), TileController.State.SELECTED);
                            for (BoardPosition pos : validSteps) {
                                board.setTileState(pos.getRow(), pos.getColumn(), TileController.State.SELECTABLE);
                            }
                            selection = tileClicked;
                        }
                        return;
                    }

                    if (validSteps.contains(tileClicked)) {
                        solitaire.performStep(selection, tileClicked);
                        selection = null;
                        validSteps = null;
                        refreshBoard();
                        checkGameEnd();
                        return;
                    }

                    validSteps = solitaire.getValidSteps(tileClicked);
                    if (validSteps.size() > 0) {
                        refreshBoard();
                        board.setTileState(event.getRow(), event.getColumn(), TileController.State.SELECTED);
                        for (BoardPosition pos : validSteps) {
                            board.setTileState(pos.getRow(), pos.getColumn(), TileController.State.SELECTABLE);
                        }
                        selection = tileClicked;
                        return;
                    }

                    selection = null;
                    validSteps = null;
                    refreshBoard();

                } catch (InvalidPositionException e) {
                } catch (InvalidStepException e) {
                }
            }
        });
    }

    private void checkGameEnd() {
        if (solitaire.isSolved()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game has been ended");
            alert.setContentText("Congratulations! You've successfully solved the puzzle!");

            alert.showAndWait();
            return;
        }

        if (solitaire.isEnded()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game has been ended");
            alert.setContentText("Sorry, you failed in solving this puzzle. Just give it another try!");

            alert.showAndWait();
            return;
        }

    }

    private void refreshBoard() {
        for (int r = 0; r < solitaire.getRowsCount(); r++) {
            for (int c = 0; c < solitaire.getColumsCount(); c++) {
                try {
                    switch (solitaire.getTile(new BoardPosition(r, c))) {
                        case MARBLE:
                            board.setTileState(r, c, TileController.State.MARBLE);
                            break;
                        case EMPTY:
                            board.setTileState(r, c, TileController.State.EMPTY);
                            break;
                    }
                } catch (InvalidPositionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void menuNewGameClicked() {
    }

}
