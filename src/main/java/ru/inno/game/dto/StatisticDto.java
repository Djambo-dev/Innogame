package ru.inno.game.dto;

import lombok.AllArgsConstructor;
import ru.inno.game.models.Player;
import ru.inno.game.repository.GamesRepository;
import ru.inno.game.repository.PlayersRepository;
import ru.inno.game.repository.ShotsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

public class StatisticDto {
    private Long gameId;
    private String firstPlayerNickname;
    private String secondPlayerNickname;
    private Integer shotsCountFromFirst;
    private Integer shotsCountFromSecond;
    private Integer firstPlayerScore;
    private Integer secondPlayerScore;
    private String playerWinner;
    private Integer localDateTime;

    public StatisticDto(Long gameId, String firstPlayer, String secondPlayer, Integer shotsCountFromFirst, Integer shotsCountFromSecond, Integer firstPlayerScore, Integer secondPlayerScore, String playerWinner, Integer localDateTime) {
        this.gameId = gameId;
        this.firstPlayerNickname = firstPlayer;
        this.secondPlayerNickname = secondPlayer;
        this.shotsCountFromFirst = shotsCountFromFirst;
        this.shotsCountFromSecond = shotsCountFromSecond;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
        this.playerWinner = playerWinner;
        this.localDateTime = localDateTime;
    }



    @Override
    public String toString() {
        return
                "Игра с ID = " + gameId + "\n" +
                        "Игрок 1: " + firstPlayerNickname + ", попаданий - " + shotsCountFromFirst + ", всего очков - " + firstPlayerScore + "\n" +
                        "Игрок 2: " + secondPlayerNickname + ", попаданий - " + shotsCountFromSecond + ", всего очков - " + secondPlayerScore + "\n" +
                        "Победа: " + playerWinner + "\n" +
                        "Игра длилась: " + localDateTime + " секунд";
    }
}
