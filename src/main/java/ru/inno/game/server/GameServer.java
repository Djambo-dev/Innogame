package ru.inno.game.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.inno.game.services.GameService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
@Data
@AllArgsConstructor
@Builder
public class GameServer {

    private PlayerThread firstPlayer;

    private PlayerThread secondPlayer;

    private ServerSocket socketPlayer;
    private GameService gameService;
    private boolean isGameStarted = false;
    public GameServer(GameService gameService){
        this.gameService = gameService;
    }
    public void start(int port) {

        try {

            socketPlayer = new ServerSocket(port);
            System.out.println("СЕРВЕР ЗАПУЩЕН...");
            System.out.println("ОЖИДАНИЕ ПОДКЛЮЧЕНИЯ ПЕРВОГО ИГРОКА...");
            firstPlayer = connect();
            System.out.println("ОЖИДАНИЕ ПОДКЛЮЧЕНИЯ ВТОРОГО ИГРОКА...");
            secondPlayer = connect();


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private PlayerThread connect() {
        Socket socketClient;
        try {
            socketClient = socketPlayer.accept();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        PlayerThread playerThread = new PlayerThread(socketClient);
        playerThread.start();
        System.out.println("ИГРОК ПОДКЛЮЧЕН");
        playerThread.sendMessage("ВЫ ПОДКЛЮЧЕНЫ");
        return playerThread;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class PlayerThread extends Thread {

        private PrintWriter toPlayer;
        private BufferedReader fromPlayer;
        private String playerNickname;
        private String ip;
        public PlayerThread(Socket socketPlayer) {

            try {
                this.toPlayer = new PrintWriter(socketPlayer.getOutputStream(), true);
                this.fromPlayer = new BufferedReader(new InputStreamReader(socketPlayer.getInputStream()));
                this.ip = socketPlayer.getInetAddress().getHostAddress();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        }

        @Override
        public void run() {

            while (true) {
                String messageFromPlayer;
                try {
                    messageFromPlayer = fromPlayer.readLine();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                if (messageFromPlayer != null) {
                    if(messageFromPlayer.startsWith("name: ")){
                        if(meFirst()){
                            firstPlayer.playerNickname = messageFromPlayer.substring(6);
                        } else {
                            secondPlayer.playerNickname = messageFromPlayer.substring(6);
                        }
                    }
                    if (meFirst()) {
                        System.out.println("ОТ ПЕРВОГО ИГРОКА ПОЛУЧЕНО: " + messageFromPlayer);
                        secondPlayer.sendMessage(messageFromPlayer);
                    } else {
                        System.out.println("ОТ ВТОРОГО ИГРОКА ПОЛУЧЕНО: " + messageFromPlayer);
                        firstPlayer.sendMessage(messageFromPlayer);
                    }


                }
                if(firstPlayer.playerNickname != null && secondPlayer.playerNickname != null && !isGameStarted){
                    gameService.startGame(firstPlayer.getIp(), secondPlayer.getIp(), firstPlayer.getPlayerNickname(), secondPlayer.getPlayerNickname());
                    isGameStarted = true;
                }
            }
        }

        public void sendMessage(String message) {
            toPlayer.println(message);
        }

        private boolean meFirst() {
            return this == firstPlayer;
        }
    }

}
