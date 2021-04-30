package ru.inno.game.repository;

import ru.inno.game.models.Game;

public interface GamesRepository {
    void save(Game game);
    Game findById(Long gameId);
    void update(Game game);
}
