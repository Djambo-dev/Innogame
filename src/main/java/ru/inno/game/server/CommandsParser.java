package ru.inno.game.server;

public class CommandsParser {

    public static boolean isMessageForDamage(String messageFromPlayer) {
        return messageFromPlayer.equals("DAMAGE");
    }
    public static boolean isMessageForShot(String messageFromPlayer) {
        return messageFromPlayer.equals("shot");
    }
    public static boolean isMessageForMove(String messageFromClient){
        return messageFromClient.equals("left") || messageFromClient.equals("right");
    }
    public static boolean isMessageForNickname(String messageFromPlayer) {
        return messageFromPlayer.startsWith("name: ");
    }
    public static boolean isMessageForExit(String messageFromPlayer) {
        return messageFromPlayer.equals("exit");
    }
}
