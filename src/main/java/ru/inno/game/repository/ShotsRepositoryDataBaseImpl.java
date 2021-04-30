package ru.inno.game.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.inno.game.models.Shot;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class ShotsRepositoryDataBaseImpl implements ShotsRepository {

    private DataSource dataSource;
    //language=SQL
    private final String SQL_INSERT_SHOT = "insert into shot(shotTime, game_id, player_shooter, player_target) values (?, ?, ?, ?)";


    //Works
    @Override
    public void save(Shot shot) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_SHOT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, shot.getShotTime().toString());
            statement.setLong(2, shot.getGame().getId());
            statement.setLong(3, shot.getShooter().getId());
            statement.setLong(4, shot.getTarget().getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Can't insert data");
            }
            try (ResultSet generatedId = statement.getGeneratedKeys()) {
                if (generatedId.next()) {
                    shot.setId(generatedId.getLong("id"));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

}
