package ru.inno.game.repository;

import ru.inno.game.models.Player;

public interface PlayersRepository {
    Player findByNickname(String nickname);
    void save(Player player);
    void update(Player player);
}
