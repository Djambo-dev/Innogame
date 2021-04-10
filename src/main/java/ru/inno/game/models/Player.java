package ru.inno.game.models;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Player {
    //private Long id;
    private String name;
    private String ip;
    private Integer score;
    private Integer maxWinsCount;
    private Integer minLosesCount;


}
