package ru.inno.game.services;

import lombok.AllArgsConstructor;
import ru.inno.game.dto.StatisticDto;
import ru.inno.game.models.Game;
import ru.inno.game.models.Player;
import ru.inno.game.models.Shot;
import ru.inno.game.repository.GamesRepository;
import ru.inno.game.repository.PlayersRepository;
import ru.inno.game.repository.ShotsRepository;

import java.time.LocalDateTime;

@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private PlayersRepository playersRepository;
    private GamesRepository gamesRepository;
    private ShotsRepository shotsRepository;

    @Override
    public Long startGame(String firstIp, String secondIp, String firstPlayerNickname, String secondPlayerNickname) {


        Player first = checkIfExists(firstIp, firstPlayerNickname);
        Player second = checkIfExists(secondIp, secondPlayerNickname);
        Game game = new Game(LocalDateTime.now(), first, second, 0, 0, 0L);
        gamesRepository.save(game);
        return game.getId();
    }

    private Player checkIfExists(String ip, String playerNickname) {
        Player player = playersRepository.findByNickname(playerNickname);
        if (player == null) {
            player = new Player(playerNickname, ip, 0, 0, 0);
            playersRepository.save(player);
        } else {
            player.setIp(ip);
            playersRepository.update(player);
        }
        return player;
    }

    @Override
    public void shot(Long gameId, String shooterNickname, String targetNickname) {
        Player shooter = playersRepository.findByNickname(shooterNickname);
        Player target = playersRepository.findByNickname(targetNickname);
        Game game = gamesRepository.findById(gameId);
        Shot shot = new Shot(LocalDateTime.now(), game, shooter, target);
        shooter.setScore(shooter.getScore() + 1);
        if (game.getFirstPlayer().getName().equals(shooterNickname)) {
            game.setShotsFromFirstPlayer(game.getShotsFromFirstPlayer() + 1);
        }
        if (game.getSecondPlayer().getName().equals(shooterNickname)) {
            game.setShotsFromSecondPlayer(game.getShotsFromSecondPlayer() + 1);
        }
        playersRepository.update(shooter);
        gamesRepository.update(game);
        shotsRepository.save(shot);
    }

    @Override
    public StatisticDto finishGame(Long gameId) {
        Game game = gamesRepository.findById(gameId);
        Player playerOne = playersRepository.findByNickname(game.getFirstPlayer().getName());

        Player playerTwo = playersRepository.findByNickname(game.getSecondPlayer().getName());

        String playerWinner = gameWinner(game);
        StatisticDto statisticDto = new StatisticDto(game.getId(), playerOne.getName(), playerTwo.getName(), game.getShotsFromFirstPlayer(), game.getShotsFromSecondPlayer(), playerOne.getScore(), playerTwo.getScore(), playerWinner, game.getDateTime().getSecond());

        return statisticDto;
    }
    private String gameWinner(Game game){
        if(game.getShotsFromFirstPlayer() > game.getShotsFromSecondPlayer()){
            Player playerOne = playersRepository.findByNickname(game.getFirstPlayer().getName());
            playerOne.setMaxWinsCount(playerOne.getMaxWinsCount() + 1);
            Player playerTwo = playersRepository.findByNickname(game.getSecondPlayer().getName());
            playerTwo.setMinLosesCount(playerTwo.getMinLosesCount() + 1);
            playersRepository.update(playerOne);
            playersRepository.update(playerTwo);
            return game.getFirstPlayer().getName();
        } else if(game.getShotsFromFirstPlayer() < game.getShotsFromSecondPlayer()){
            Player playerOne = playersRepository.findByNickname(game.getFirstPlayer().getName());
            playerOne.setMinLosesCount(playerOne.getMinLosesCount() + 1);
            Player playerTwo = playersRepository.findByNickname(game.getSecondPlayer().getName());
            playerTwo.setMaxWinsCount(playerTwo.getMaxWinsCount() + 1);
            playersRepository.update(playerOne);
            playersRepository.update(playerTwo);
            return game.getSecondPlayer().getName();
        } else
            return "Ничья";
    }
}
