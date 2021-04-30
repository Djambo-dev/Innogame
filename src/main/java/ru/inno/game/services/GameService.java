package ru.inno.game.services;

import ru.inno.game.dto.StatisticDto;

public interface GameService {
    /**
     * Method for game starting. If user with same nickname already exist, then we work with it.
     * If doesn't exist - creating new.
     *
     * @param firstIp            IP-address that using first player
     * @param secondIp           IP-address that using second player
     * @param firstUserNickname  first user nickname
     * @param secondUserNickname second user nickname
     * @return game identification
     */
    Long startGame(String firstIp, String secondIp, String firstUserNickname, String secondUserNickname);

    /**
     * Recording shots (hits)
     * @param gameId game identification
     * @param shooterNickname first player name
     * @param targetNickname second player name
     */
    void shot(Long gameId, String shooterNickname, String targetNickname);

    StatisticDto finishGame(Long gameId, Long seconds);
}
