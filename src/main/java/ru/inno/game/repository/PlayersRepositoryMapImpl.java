package ru.inno.game.repository;

import lombok.AllArgsConstructor;
import ru.inno.game.models.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayersRepositoryMapImpl implements PlayersRepository {

    private Map<String, Player> players;
    public PlayersRepositoryMapImpl(){
        players = new HashMap<>();
    }
    @Override
    public Player findByNickname(String nickname) {
        return players.get(nickname);
    }

    @Override
    public void save(Player player) {
        players.put(player.getName(), player);

    }

    @Override
    public void update(Player player) {
        if(players.containsKey(player.getName())){
        players.put(player.getName(), player);
    }else {
            System.err.println("Нельзя обновить несуществующего игрока");
        }
    }
}
