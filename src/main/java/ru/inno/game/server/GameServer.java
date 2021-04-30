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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@AllArgsConstructor
@Builder
public class GameServer {

    private PlayerThread firstPlayer;
    private PlayerThread secondPlayer;
    private Long gameId;
    private Long startTimeMills;
    private ServerSocket socketPlayer;
    private GameService gameService;
    private boolean isGameStarted = false;
    private boolean isGameInProcess = true;
    private Lock lock = new ReentrantLock();
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
            while (isGameInProcess) {
                String messageFromPlayer;
                try {
                    messageFromPlayer = fromPlayer.readLine();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                if (messageFromPlayer != null) {
                    if(isMessageForNickname(messageFromPlayer)){
                        resolveNickname(messageFromPlayer);
                    } else if(isMessageForExit(messageFromPlayer) && isGameInProcess){
                       lock.lock();
                       gameService.finishGame(gameId, (System.currentTimeMillis() - startTimeMills) / 1000);
                       isGameInProcess = false;
                       lock.unlock();
                    }
                }
                lock.lock();
                if(isGameReadyForStart()){
                    gameId = gameService.startGame(firstPlayer.getIp(), secondPlayer.getIp(), firstPlayer.getPlayerNickname(), secondPlayer.getPlayerNickname());
                    startTimeMills = System.currentTimeMillis();
                    isGameStarted = true;
                }
                lock.unlock();
            }
        }

        private boolean isMessageForExit(String messageFromPlayer) {
            return messageFromPlayer.equals("exit");
        }

        private boolean isMessageForNickname(String messageFromPlayer) {
            return messageFromPlayer.startsWith("name: ");
        }

        private boolean isGameReadyForStart() {
            return firstPlayer.playerNickname != null && secondPlayer.playerNickname != null && !isGameStarted;
        }

        private void resolveNickname(String message) {
            if(meFirst()){
                recordNickname(message, firstPlayer, "ИМЯ ПЕРВОГО ИГРОКА: ", secondPlayer);
            } else {
                recordNickname(message, secondPlayer, "ИМЯ ВТОРОГО ИГРОКА: ", firstPlayer);
            }
        }

        private void recordNickname(String nickname, PlayerThread currentPlayer, String anotherMessagePrefix, PlayerThread anotherPlayer) {
            currentPlayer.playerNickname = nickname.substring(6);
            System.out.println(anotherMessagePrefix + nickname);
            anotherPlayer.sendMessage(nickname);
        }

        public void sendMessage(String message) {
            toPlayer.println(message);
        }

        private boolean meFirst() {
            return this == firstPlayer;
        }
    }

}
