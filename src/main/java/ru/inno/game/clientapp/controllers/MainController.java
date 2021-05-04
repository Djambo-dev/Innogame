package ru.inno.game.clientapp.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import ru.inno.game.clientapp.socket.SocketClient;
import ru.inno.game.clientapp.utils.GameUtils;

import java.net.URL;
import java.util.ResourceBundle;
@Getter
public class MainController implements Initializable {
    private SocketClient socketClient;

    public GameUtils getGameUtils() {
        return gameUtils;
    }

    private GameUtils gameUtils;

    public Circle getPlayer() {
        return player;
    }

    @FXML
    private Circle player;
    @FXML
    private Label hpPlayer;
    @FXML
    private Label hpEnemy;


    @FXML
    private Circle enemy;
    @FXML
    private Button buttonGo;
    @FXML
    private Button buttonConnect;
    @FXML
    private TextField textPlayerName;
    @FXML
    private AnchorPane pane;

    public EventHandler<KeyEvent> getKeyEventEventHandler() {
        return keyEventEventHandler;
    }

    private EventHandler<KeyEvent> keyEventEventHandler = event -> {
        if (event.getCode() == KeyCode.RIGHT) {
            gameUtils.goRight(player);
            socketClient.sendMessage("right");
        } else if ((event.getCode() == KeyCode.LEFT)) {
            gameUtils.goLeft(player);
            socketClient.sendMessage("left");
        } else if ((event.getCode() == KeyCode.SPACE)){
            Circle bullet = gameUtils.createBulletFor(player, false);
            socketClient.sendMessage("shot");
        }
    };



    public Circle getEnemy() {
        return enemy;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameUtils = new GameUtils();

        buttonConnect.setOnAction(event -> {
            socketClient = new SocketClient(this, "localhost", 7777);
            new Thread(socketClient).start();
            buttonConnect.setDisable(true);
            buttonGo.setDisable(false);
            textPlayerName.setDisable(false);
            gameUtils.setPane(pane);
            gameUtils.setSocketClient(socketClient);
        });
        buttonGo.setOnAction(event -> {
            socketClient.sendMessage("name: " + textPlayerName.getText());
            buttonGo.setDisable(true);
            textPlayerName.setDisable(true);
            buttonGo.getScene().getRoot().requestFocus();
        });
        gameUtils.setMainController(this);

    }
}
