package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HighScoreTableController extends VBox {
    private Scene gameScene;

    public HighScoreTableController(Scene gameScene) {
        this.gameScene = gameScene;

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/highscoretable.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void backToGame(){
        ((Stage)this.getScene().getWindow()).setScene(gameScene);
    }

    public void exitGame(){
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }
}
