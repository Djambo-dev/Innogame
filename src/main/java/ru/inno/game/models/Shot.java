package ru.inno.game.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.StringJoiner;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shot {

    private Long id;
    private LocalDateTime shotTime;
    private boolean isHit;
    private Game game;
    private Player shooter;
    private Player target;

    public Shot(LocalDateTime shotTime, Game game, Player shooter, Player target) {
        this.shotTime = shotTime;
        this.game = game;
        this.shooter = shooter;
        this.target = target;
    }


}
