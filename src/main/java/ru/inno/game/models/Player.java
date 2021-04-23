package ru.inno.game.models;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    private Long id = 0L;
    private String name;
    private String ip;
    private Integer score;
    private Integer winsCount;
    private Integer losesCount;

    public Player(String name, String ip, Integer score, Integer winsCount, Integer losesCount) {
        ++id;
        this.name = name;
        this.ip = ip;
        this.score = score;
        this.winsCount = winsCount;
        this.losesCount = losesCount;
    }
}
