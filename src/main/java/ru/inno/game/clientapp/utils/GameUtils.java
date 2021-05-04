package ru.inno.game.clientapp.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GameUtils {

    public void setPane(AnchorPane pane) {
        this.pane = pane;
    }

    private AnchorPane pane;
    private static final int PLAYER_STEP = 10;
    public void goRight(Circle player) {
        player.setCenterX(player.getCenterX() + PLAYER_STEP);
    }

    public void goLeft(Circle player) {
        player.setCenterX(player.getCenterX() - PLAYER_STEP);
    }

    public Circle createBulletFor(Circle player, boolean isDown) {
        Circle bullet = new Circle();
        bullet.setRadius(5);
        pane.getChildren().add(bullet);
        bullet.setCenterX(player.getCenterX() + player.getLayoutX());
        bullet.setCenterY(player.getCenterY() + player.getLayoutY());
        bullet.setFill(Color.ORANGE);

        int value;
        if(isDown){
            value = 1;
        } else {
            value = -1;
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), animation ->{
            bullet.setCenterY(bullet.getCenterY() + value);
        }));
        timeline.setCycleCount(500);
        timeline.play();
        return bullet;
    }
}
