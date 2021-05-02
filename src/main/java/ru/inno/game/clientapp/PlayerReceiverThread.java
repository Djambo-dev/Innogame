package ru.inno.game.clientapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerReceiverThread extends Thread {
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    public PlayerReceiverThread(String host, int port){
        try {
            socket= new Socket(host, port);
            toServer = new PrintWriter(socket.getOutputStream(),true);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    @Override
    public void run(){

        while (true){
            String messageFromServer;
            try {
                messageFromServer = fromServer.readLine();
                if(messageFromServer != null){
                    System.out.println("ОТ СЕРВЕРА ПОЛУЧЕНО: " + messageFromServer);
                }
            } catch (IOException e){
                throw new IllegalStateException(e);
            }
        }
    }
    public void sendMessage(String message){
        toServer.println(message);
    }
}
