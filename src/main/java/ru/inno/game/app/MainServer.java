package ru.inno.game.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.inno.game.repository.*;
import ru.inno.game.server.GameServer;
import ru.inno.game.services.GameService;
import ru.inno.game.services.GameServiceImpl;

import javax.sql.DataSource;

public class MainServer {
    public static void main(String[] args) {
        final String JDBC_URL = "jdbc:postgresql://localhost:5432/Innogame";
        final String JDBC_USER = "postgres";
        final String JDBC_PASSWORD = "qwerty";
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(JDBC_URL);
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setUsername(JDBC_USER);
        hikariConfig.setPassword(JDBC_PASSWORD);
        hikariConfig.setMaximumPoolSize(20);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        PlayersRepository playersRepository = new PlayersRepositoryDataBaseImpl(dataSource);
        GamesRepository gamesRepository = new GamesRepositoryDataBaseImpl(dataSource);
        ShotsRepository shotsRepository = new ShotsRepositoryDataBaseImpl(dataSource);
        GameService gameService = new GameServiceImpl(playersRepository,gamesRepository,shotsRepository);
        GameServer gameServer = new GameServer(gameService);
        gameServer.start(7777);

    }

}
