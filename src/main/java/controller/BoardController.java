package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class BoardController extends StackPane {
    private TileController[][] tiles;

    @FXML
    private ImageView english;
    @FXML
    private ImageView european;
    @FXML
    private GridPane board;

    public BoardController(Integer rows, Integer columns) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/board.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.tiles = new TileController[rows][columns];

        for (int i = 0; i < rows; i++){
            this.board.addRow(0);
        }

        for (int i = 0; i < columns; i++){
            this.board.addColumn(0);
        }
    }

    public void setBoardType (BoardType type){
        if (type == BoardType.ENGLISH) {
            english.setVisible(true);
            european.setVisible(false);
        } else {
            english.setVisible(false);
            european.setVisible(true);
        }
    }

    public void setTileState(Integer row, Integer column, TileState state){
        if (tiles[row][column] == null){
            tiles[row][column] = new TileController();
            tiles[row][column].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    fireEvent(new TileClickedEvent(board.getRowIndex((Node)mouseEvent.getSource()), board.getColumnIndex((Node)mouseEvent.getSource())));
                }
            });

            board.add(tiles[row][column], column, row);
            board.setHalignment(tiles[row][column], HPos.CENTER);
            board.setValignment(tiles[row][column], VPos.CENTER);
        }

        tiles[row][column].setState(state);
    }


}
