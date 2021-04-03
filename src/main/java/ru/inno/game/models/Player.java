package ru.inno.game.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Player {
    //private Long id;
    private String ip;
    private String name;
    private Integer score;
    private Integer maxWinsCount;
    private Integer minLosesCount;


}
