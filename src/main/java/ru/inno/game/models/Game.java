package ru.inno.game.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.StringJoiner;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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


}
