package ru.inno.game.clientapp.socket;

import javafx.application.Platform;
import javafx.scene.control.Label;
import ru.inno.game.clientapp.controllers.MainController;
import ru.inno.game.clientapp.utils.GameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient extends Thread {
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private MainController mainController;
    private GameUtils gameUtils;

    public SocketClient(MainController mainController, String host, int port) {
        try {
            socket = new Socket(host, port);
            toServer = new PrintWriter(socket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.mainController = mainController;
            this.gameUtils = mainController.getGameUtils();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void run() {

        while (true) {
            String messageFromServer;
            if (Integer.parseInt(mainController.getHpPlayer().getText()) <= 0 || Integer.parseInt(mainController.getHpEnemy().getText()) <= 0) {
                sendMessage("exit");
            }
            try {
                messageFromServer = fromServer.readLine();
                if (messageFromServer.startsWith("Игра с")) {
                    Platform.runLater(() -> mainController.getResult().setText(messageFromServer));
                } else {

                    switch (messageFromServer) {
                        case "left":
                            gameUtils.goLeft(mainController.getEnemy());
                            break;
                        case "right":
                            gameUtils.goRight(mainController.getEnemy());
                            break;
                        case "shot":
                            Platform.runLater(() -> gameUtils.createBulletFor(mainController.getEnemy(), true));
                            break;
                    }

                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        }
    }

    public void sendMessage(String message) {
        toServer.println(message);
    }
}
