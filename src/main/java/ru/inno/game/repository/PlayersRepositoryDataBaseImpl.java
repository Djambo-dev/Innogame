package ru.inno.game.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.inno.game.models.Player;

import javax.sql.DataSource;
import java.sql.*;

@Data
@AllArgsConstructor
@Builder
public class PlayersRepositoryDataBaseImpl implements PlayersRepository {

    private DataSource dataSource;
    //language=SQL
    private final String SQL_INSERT_PLAYER = "insert into player(player_name, ip, points, wins_count, loses_count) values (?,?,?,?,?)";
    //language=SQL
    private final String SQL_FIND_PLAYER_BY_NICKNAME = "select * from player where player_name = ?";
    //language=sql
    private final String SQL_UPDATE_PLAYER = "update player set player_name = ?, ip = ?, points = ?, wins_count = ?, loses_count = ? where id = ?";

    @Override
    public void update(Player player) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PLAYER)) {
            preparedStatement.setLong(6, player.getId());
            insertData(player, preparedStatement);
        }catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        }




    //Works
    @Override
    public Player findByNickname(String nickname) {
        Player player = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PLAYER_BY_NICKNAME)) {
            preparedStatement.setString(1, nickname);
            try (ResultSet row = preparedStatement.executeQuery()) {
                if (row.next()) {
                    player = Player.builder()
                            .id(row.getLong("id"))
                            .name(row.getString("player_name"))
                            .ip(row.getString("ip"))
                            .score(row.getInt("points"))
                            .winsCount(row.getInt("wins_count"))
                            .losesCount(row.getInt("loses_count"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return player;
    }

    //Works
    @Override
    public void save(Player player) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PLAYER, Statement.RETURN_GENERATED_KEYS)) {
            insertData(player, preparedStatement);
            try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
                if (generatedId.next()) {
                    player.setId(generatedId.getLong("id"));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    private void insertData(Player player, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, player.getName());
        preparedStatement.setString(2, player.getIp());
        preparedStatement.setInt(3, player.getScore());
        preparedStatement.setInt(4, player.getWinsCount());
        preparedStatement.setInt(5, player.getLosesCount());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 1) {
            throw new SQLException("Can't insert data");
        }
    }




}
