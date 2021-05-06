package ru.inno.game.clientapp.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import ru.inno.game.clientapp.controllers.MainController;
import ru.inno.game.clientapp.socket.SocketClient;

@Getter
public class GameUtils {

    private static final int PLAYER_STEP = 10;
    private static final int DAMAGE = 5;

    private MainController mainController;
    private AnchorPane pane;


    private SocketClient socketClient;


    public void goRight(Circle player) {
        if(!isSeparatorIntersects(mainController.getRightFieldSeparator(), player)) {
            player.setCenterX(player.getCenterX() + PLAYER_STEP);
        } else {
            goLeft(player);
        }
    }

    public void goLeft(Circle player) {
        if(!isSeparatorIntersects(mainController.getLeftFieldSeparator(), player)){
            player.setCenterX(player.getCenterX() - PLAYER_STEP);
        } else {
            goRight(player);
        }


    }

    public Circle createBulletFor(Circle player, boolean isEnemy) {
        Circle bullet = new Circle();
        final Circle target;
        final Label targetHp;
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
            if (bullet.isVisible() && isIntersects(bullet, target)) {
                //уменьшаем здоровье
                createDamage(targetHp);
                //скрыть пулю
                bullet.setVisible(false);
                if (!isEnemy) {
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
    private boolean isSeparatorIntersects(Separator separator, Circle player){
        return player.getBoundsInParent().intersects(separator.getBoundsInParent());
    }
    private void createDamage(Label hpLabel) {
        int hpPlayer = Integer.parseInt(hpLabel.getText());
        if (hpPlayer == 0 || hpPlayer < 0) {
            hpLabel.setText(String.valueOf(0));
        } else {
            hpLabel.setText(String.valueOf(hpPlayer - DAMAGE));
        }
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
