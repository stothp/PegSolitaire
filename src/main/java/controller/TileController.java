package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TileController extends Pane {
    private Node currentNode;
    private Node nodeEmpty, nodeMarble, nodeSelected, nodeSelectable;
    private TileState currentState;

    public TileController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/tile.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        try {
            this.nodeEmpty = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/tile_empty.fxml"));
            this.nodeMarble = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/tile_with_marble.fxml"));
            this.nodeSelected = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/tile_selected.fxml"));
            this.nodeSelectable = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/tile_selectable.fxml"));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.currentNode = this.nodeEmpty;
        this.getChildren().add(currentNode);
    }

    private void refreshNode(){
        getChildren().remove(currentNode);
        switch (currentState){
            case EMPTY:
                currentNode = nodeEmpty;
                break;
            case MARBLE:
                currentNode = nodeMarble;
                break;
            case SELECTED:
                currentNode = nodeSelected;
                break;
            case SELECTABLE:
                currentNode = nodeSelectable;
                break;
        }
        getChildren().add(currentNode);
    }

    public void setState(TileState state){
        if (this.currentState == state) {
            return;
        }

        this.currentState = state;
        refreshNode();
    }
}
