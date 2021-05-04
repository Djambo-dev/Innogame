package ru.inno.game.clientapp.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ru.inno.game.clientapp.controllers.MainController;
import ru.inno.game.clientapp.socket.SocketClient;

public class GameUtils {

    private static final int PLAYER_STEP = 10;
    private static final int DAMAGE = 5;

    private MainController mainController;
    private AnchorPane pane;



    private SocketClient socketClient;


    public void goRight(Circle player) {
        player.setCenterX(player.getCenterX() + PLAYER_STEP);
    }

    public void goLeft(Circle player) {
        player.setCenterX(player.getCenterX() - PLAYER_STEP);
    }

    public Circle createBulletFor(Circle player, boolean isEnemy) {
        Circle bullet = new Circle();
        bullet.setRadius(5);
        pane.getChildren().add(bullet);
        bullet.setCenterX(player.getCenterX() + player.getLayoutX());
        bullet.setCenterY(player.getCenterY() + player.getLayoutY());
        bullet.setFill(Color.ORANGE);

        int value;
        if (isEnemy) {
            value = 1;
        } else {
            value = -1;
        }
        final Circle target;
        final Label targetHp;
        if (!isEnemy) {
            target = mainController.getEnemy();
            targetHp = mainController.getHpEnemy();
        } else {
            target = mainController.getPlayer();
            targetHp = mainController.getHpPlayer();
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), animation -> {
            bullet.setCenterY(bullet.getCenterY() + value);
                //если пуля еще видна и произошло пересечение
               if(bullet.isVisible() && isIntersects(bullet,target)){
                   //уменьшаем здоровье
                   createDamage(targetHp);
                   //скрыть пулю
                   bullet.setVisible(false);
                  if(!isEnemy){
                      socketClient.sendMessage("DAMAGE");
                  }
               }
        }));
        timeline.setCycleCount(500);
        timeline.play();
        return bullet;
    }

    private boolean isIntersects(Circle bullet, Circle player) {
        return bullet.getBoundsInParent().intersects(player.getBoundsInParent());
    }

    private void createDamage(Label hpLabel) {
        int hpPlayer = Integer.parseInt(hpLabel.getText());
        hpLabel.setText(String.valueOf(hpPlayer - DAMAGE));
    }
    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }
}
