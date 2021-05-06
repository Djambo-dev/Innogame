package ru.inno.game.dto;

public class StatisticDto {
    private Long gameId;
    private String firstPlayerNickname;
    private String secondPlayerNickname;
    private Integer shotsCountFromFirst;
    private Integer shotsCountFromSecond;
    private Integer firstPlayerScore;
    private Integer secondPlayerScore;
    private String playerWinner;
    private Long gameTimeInSeconds;

    public StatisticDto(Long gameId, String firstPlayer, String secondPlayer, Integer shotsCountFromFirst, Integer shotsCountFromSecond, Integer firstPlayerScore, Integer secondPlayerScore, String playerWinner, Long gameTimeInSeconds) {
        this.gameId = gameId;
        this.firstPlayerNickname = firstPlayer;
        this.secondPlayerNickname = secondPlayer;
        this.shotsCountFromFirst = shotsCountFromFirst;
        this.shotsCountFromSecond = shotsCountFromSecond;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
        this.playerWinner = playerWinner;
        this.gameTimeInSeconds = gameTimeInSeconds;
    }



    @Override
    public String toString() {
        return
                "Игра с ID = " + gameId + " " +
                        "Игрок 1: " + firstPlayerNickname + ", попаданий - " + shotsCountFromFirst + ", всего очков - " + firstPlayerScore + " " +
                        "Игрок 2: " + secondPlayerNickname + ", попаданий - " + shotsCountFromSecond + ", всего очков - " + secondPlayerScore + " " +
                        "Победа: " + playerWinner + " " +
                        "Игра длилась: " + gameTimeInSeconds + " секунд";
    }
}
/*
"Игра с ID = " + gameId + "\n" +
        "Игрок 1: " + firstPlayerNickname + ", попаданий - " + shotsCountFromFirst + ", всего очков - " + firstPlayerScore + "\n" +
        "Игрок 2: " + secondPlayerNickname + ", попаданий - " + shotsCountFromSecond + ", всего очков - " + secondPlayerScore + "\n" +
        "Победа: " + playerWinner + "\n" +
        "Игра длилась: " + gameTimeInSeconds + " секунд";*/
