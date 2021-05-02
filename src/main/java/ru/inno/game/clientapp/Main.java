package ru.inno.game.clientapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlayerReceiverThread playerReceiverThread = new PlayerReceiverThread("localhost", 7777);
        playerReceiverThread.start();
        while (true) {
            String messageToServer = scanner.nextLine();
            playerReceiverThread.sendMessage(messageToServer);
        }


    }
}
