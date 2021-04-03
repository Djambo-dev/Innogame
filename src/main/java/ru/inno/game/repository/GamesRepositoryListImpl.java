package ru.inno.game.repository;

import ru.inno.game.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GamesRepositoryListImpl implements GamesRepository {
    private List<Game> games;
    public GamesRepositoryListImpl(){
        games = new ArrayList<>();
    }
    @Override
    public void save(Game game) {
    game.setId((long) games.size());
    games.add(game);
    }

    @Override
    public Game findById(Long gameId) {
        return games.get(gameId.intValue());
    }

    @Override
    public void update(Game game) {
        games.set(game.getId().intValue(), game);
    }
}
