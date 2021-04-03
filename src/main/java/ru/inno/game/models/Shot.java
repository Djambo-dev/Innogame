package ru.inno.game.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.StringJoiner;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Shot {
    //private Long id;
    private LocalDateTime shotTime;
    //private boolean isHit;
    private Game game;
    private Player shooter;
    private Player target;

    @Override
    public String toString() {
        return new StringJoiner(", ", Shot.class.getSimpleName() + "[", "]")
                .add("shotTime=" + shotTime)
                .add("game=" + game.getId())
                .add("shooter=" + shooter.getName())
                .add("target=" + target.getName())
                .toString();
    }
}
