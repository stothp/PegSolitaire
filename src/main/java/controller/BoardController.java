package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class BoardController extends GridPane {
    private TileController[][] tiles;

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
            this.addRow(0);
        }

        for (int i = 0; i < columns; i++){
            this.addColumn(0);
        }
    }


    public void setTileState(Integer row, Integer column, TileState state){
        if (tiles[row][column] == null){
            tiles[row][column] = new TileController();
            tiles[row][column].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    fireEvent(new TileClickedEvent(getRowIndex((Node)mouseEvent.getSource()), getColumnIndex((Node)mouseEvent.getSource())));
                }
            });

            add(tiles[row][column], column, row);
        }

        tiles[row][column].setState(state);
    }


}
