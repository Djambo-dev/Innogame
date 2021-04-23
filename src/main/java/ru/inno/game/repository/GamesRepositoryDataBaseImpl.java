package ru.inno.game.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.inno.game.models.Game;
import ru.inno.game.models.Player;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamesRepositoryDataBaseImpl implements GamesRepository {
    private final String SQL_UPDATE = " ";

    //language=SQL
    private final String SQL_INSERT_GAME = "insert into game(dateTime, player_first, player_second, player_first_shots_count, player_second_shots_count, game_time_duration) values (?,?,?,?,?,?)";
    //language=SQL
    private final String SQL_FIND_GAME_BY_ID = "select * from game where id = ?";
    //language=SQL
    private final String SQL_FIND_PLAYER_BY_ID = "select * from player where id = ?";
    private DataSource dataSource;

    //Works
    @Override
    public void save(Game game) {

        ResultSet generatedId;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_GAME, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, game.getDateTime().toString());
            preparedStatement.setLong(2, game.getFirstPlayer().getId());
            preparedStatement.setLong(3, game.getSecondPlayer().getId());
            preparedStatement.setInt(4, game.getShotsFromFirstPlayer());
            preparedStatement.setInt(5, game.getShotsFromSecondPlayer());
            preparedStatement.setLong(6, game.getGameDurationInSeconds());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Can't insert data");
            }
            generatedId = preparedStatement.getGeneratedKeys();
            if (generatedId.next()) {
                game.setId(generatedId.getLong("id"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    //works
    @Override
    public Game findById(Long gameId) {

        ResultSet row;

        Game game = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_GAME_BY_ID)) {
            preparedStatement.setLong(1, gameId);
            row = preparedStatement.executeQuery();
            if (row.next()) {

                game = Game.builder().id(row.getLong("id"))
                        .dateTime(LocalDateTime.parse(row.getString("dateTime")))
                        .firstPlayer(findPlayerById(row.getLong("player_first")))
                        .secondPlayer(findPlayerById(row.getLong("player_second")))
                        .shotsFromFirstPlayer(row.getInt("player_first_shots_count"))
                        .shotsFromSecondPlayer(row.getInt("player_second_shots_count"))
                        .gameDurationInSeconds(row.getLong("game_time_duration"))
                        .build();


            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return game;
    }

    @Override
    public void update(Game game) {


    }
    //works
    private Player findPlayerById(Long playerId) {
        Player player = null;
        ResultSet row;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PLAYER_BY_ID)) {
            preparedStatement.setLong(1, playerId);
            row = preparedStatement.executeQuery();
            if (row.next()) {
                player = Player.builder()
                        .id(row.getLong("id"))
                        .name(row.getString("player_name"))
                        .ip(row.getString("ip"))
                        .score(row.getInt("points"))
                        .winsCount((row.getInt("wins_count")))
                        .losesCount(row.getInt("loses_count"))
                        .build();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return player;
    }
}
