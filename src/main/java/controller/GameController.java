package controller;

import gamelogic.BoardPosition;
import gamelogic.InvalidPositionException;
import gamelogic.InvalidStepException;
import gamelogic.PegSolitaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

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
    private static final String[] europeanBoardDef = {
            "  OOO  ",
            " OOOOO ",
            "OOOOOOO",
            "OOO.OOO",
            "OOOOOOO",
            " OOOOO ",
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

    @FXML
    private TextField name;

    @FXML
    private ComboBox<BoardTypePair> boardType;

    public GameController() {
    }

    @FXML
    public void initialize() {
        ObservableList<BoardTypePair> boardList = FXCollections.observableArrayList();
        BoardTypePair english = new BoardTypePair(BoardType.ENGLISH, "English");
        boardList.add(english);
        boardList.add(new BoardTypePair(BoardType.EUROPEAN, "European"));
        boardType.setItems(boardList);
        boardType.getSelectionModel().select(english);
//        startNewGame();
    }

    public void startNewGame() {
//        GameData gd = new GameData();
//        gd.setBoardType(BoardType.ENGLISH);
//        gd.setName("STP");
//        gd.setRemainingMarbles(2);
//        GameDataDAO gdd = GameDataDAO.getInstance();
//        gdd.persist(gd);
        if (name.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing name");
            alert.setContentText("Please give your name.\nUse the textbox in the middle.");

            alert.showAndWait();
            return;
        }



        String[] boardDef;
        switch (boardType.getSelectionModel().getSelectedItem().getKey()) {
            case EUROPEAN:
                boardDef = europeanBoardDef;
                break;
            default:
                boardDef = englishBoardDef;
        }
        solitaire = new PegSolitaire(boardDef);
        //mainContainer.getChildren().add(new Label("Heló"));
        //vbox1.setVgrow(pane1, Priority.ALWAYS);
        if (this.board != null) {
            pane1.getChildren().remove(this.board);
        }
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
                            board.setTileState(event.getRow(), event.getColumn(), TileState.SELECTED);
                            for (BoardPosition pos : validSteps) {
                                board.setTileState(pos.getRow(), pos.getColumn(), TileState.SELECTABLE);
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
                        board.setTileState(event.getRow(), event.getColumn(), TileState.SELECTED);
                        for (BoardPosition pos : validSteps) {
                            board.setTileState(pos.getRow(), pos.getColumn(), TileState.SELECTABLE);
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

    public void showHighScore(){
        ((Stage)vbox1.getScene().getWindow()).setScene(new Scene(new HighScoreTableController(vbox1.getScene())));
    }

    public void exitGame(){
        Stage stage = (Stage) vbox1.getScene().getWindow();
        stage.close();
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
            alert.setContentText("Sorry, you failed in solving this puzzle.\nJust give it another try!");

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
                            board.setTileState(r, c, TileState.MARBLE);
                            break;
                        case EMPTY:
                            board.setTileState(r, c, TileState.EMPTY);
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
