package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.validator.internal.util.logging.formatter.DurationFormatter;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HighScoreTableController extends VBox {
    private Scene gameScene;

    GameDataDAO dao = GameDataDAO.getInstance();

    @FXML
    private TableView<GameData> engTable;
    @FXML
    private TableColumn<GameData, String> engPlayer;
    @FXML
    private TableColumn<GameData, Integer> engMarbles;
    @FXML
    private TableColumn<GameData, Duration> engDuration;
    @FXML
    private TableColumn<GameData, ZonedDateTime> engStarted;
    @FXML
    private TableView<GameData> eurTable;
    @FXML
    private TableColumn<GameData, String> eurPlayer;
    @FXML
    private TableColumn<GameData, Integer> eurMarbles;
    @FXML
    private TableColumn<GameData, Duration> eurDuration;
    @FXML
    private TableColumn<GameData, ZonedDateTime> eurStarted;    

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

    public void initialize(){
        engPlayer.setCellValueFactory(new PropertyValueFactory<>("name"));
        engMarbles.setCellValueFactory(new PropertyValueFactory<>("remainingMarbles"));
        engDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        engStarted.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        engDuration.setCellFactory(column -> {
            TableCell<GameData, Duration> cell = new TableCell<GameData, Duration>() {
                @Override
                protected void updateItem(Duration item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        long s = item.toSeconds();
                        setText(String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));
                    }
                }
            };

            return cell;
        });

        engStarted.setCellFactory(column -> {
            TableCell<GameData, ZonedDateTime> cell = new TableCell<GameData, ZonedDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss Z");

                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(item.format(formatter));
                    }
                }
            };

            return cell;
        });

        ObservableList<GameData> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(dao.listData(10, BoardType.ENGLISH));
        engTable.setItems(observableResult);

        eurPlayer.setCellValueFactory(new PropertyValueFactory<>("name"));
        eurMarbles.setCellValueFactory(new PropertyValueFactory<>("remainingMarbles"));
        eurDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        eurStarted.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        eurDuration.setCellFactory(column -> {
            TableCell<GameData, Duration> cell = new TableCell<GameData, Duration>() {
                @Override
                protected void updateItem(Duration item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        long s = item.toSeconds();
                        setText(String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));
                    }
                }
            };

            return cell;
        });

        eurStarted.setCellFactory(column -> {
            TableCell<GameData, ZonedDateTime> cell = new TableCell<GameData, ZonedDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss Z");

                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(item.format(formatter));
                    }
                }
            };

            return cell;
        });

        ObservableList<GameData> observableResult2 = FXCollections.observableArrayList();
        observableResult2.addAll(dao.listData(10, BoardType.EUROPEAN));
        eurTable.setItems(observableResult2);
    }

    public void backToGame(){
        ((Stage)this.getScene().getWindow()).setScene(gameScene);
    }

    public void exitGame(){
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }
}
