package ru.inno.game.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.StringJoiner;


@Getter
@Setter
@EqualsAndHashCode
public class Game {
    private Long id = 0L;

    private LocalDateTime dateTime;
    private Player firstPlayer;
    private Player secondPlayer;
    private Integer shotsFromFirstPlayer;
    private Integer shotsFromSecondPlayer;
    private Long gameDurationInSeconds;

    public Game(LocalDateTime dateTime, Player firstPlayer, Player secondPlayer, Integer shotsFromFirstPlayer, Integer shotsFromSecondPlayer, Long gameDurationInSeconds) {
        ++id;
        this.dateTime = dateTime;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.shotsFromFirstPlayer = shotsFromFirstPlayer;
        this.shotsFromSecondPlayer = shotsFromSecondPlayer;
        this.gameDurationInSeconds = gameDurationInSeconds;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Game.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("dateTime=" + dateTime)
                .add("firstPlayer=" + firstPlayer.getName())
                .add("secondPlayer=" + secondPlayer.getName())
                .add("shotsFromFirstPlayer=" + shotsFromFirstPlayer)
                .add("shotsFromSecondPlayer=" + shotsFromSecondPlayer)
                .add("gameDurationInSeconds=" + gameDurationInSeconds)
                .toString();
    }
}
