package ru.inno.game.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.inno.game.dto.StatisticDto;
import ru.inno.game.models.Game;
import ru.inno.game.models.Player;
import ru.inno.game.models.Shot;
import ru.inno.game.repository.*;
import ru.inno.game.services.GameService;
import ru.inno.game.services.GameServiceImpl;
import ru.inno.game.utils.CustomDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String JDBC_URL = "jdbc:postgresql://localhost:5432/Innogame";
        final String JDBC_USER = "postgres";
        final String JDBC_PASSWORD = "qwerty";
        /*HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(JDBC_URL);
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setUsername(JDBC_USER);
        hikariConfig.setPassword(JDBC_PASSWORD);
        hikariConfig.setMaximumPoolSize(20);
        DataSource dataSource = new HikariDataSource(hikariConfig);*/
        DataSource dataSource = new CustomDataSource(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
        /*PlayersRepository playersRepository = new PlayersRepositoryDataBaseImpl(dataSource);
        Player playerOne = new Player("Bob", "222", 3, 1,0);

         Player playerTwo = new Player("Alex", "1111", 6, 0,1);
        playersRepository.save(playerOne);
        playersRepository.save(playerTwo);
        Game game = new Game(LocalDateTime.now(), playerOne, playerTwo,6,5,300L);
        GamesRepository gamesRepository = new GamesRepositoryDataBaseImpl(dataSource);
        gamesRepository.save(game);
        ShotsRepository shotsRepository = new ShotsRepositoryDataBaseImpl(dataSource);
        shotsRepository.save(new Shot(LocalDateTime.now(), game, playerOne,playerTwo));
        Player playerFind = playersRepository.findByNickname("Alex");
        System.out.println(playerFind.getScore());
        Game gameTest = gamesRepository.findById(1L);
        System.out.println(gameTest);*/
        PlayersRepository playersRepository = new PlayersRepositoryDataBaseImpl(dataSource);
        GamesRepository gamesRepository = new GamesRepositoryDataBaseImpl(dataSource);
        ShotsRepository shotsRepository = new ShotsRepositoryDataBaseImpl(dataSource);
        GameService gameService = new GameServiceImpl(playersRepository, gamesRepository, shotsRepository);
        Scanner scanner = new Scanner(System.in);
        String first = scanner.nextLine();
        String second = scanner.nextLine();
        Random random = new Random();
        Long gameId = gameService.startGame("127.0.0.1", "127.0.0.2", first, second);
        String shooter = first;
        String target = second;
        int i1 = 0;
        while (i1 < 10) {
            System.out.println(shooter + " делайте выстрел в " + target);
            scanner.nextLine();
            int success = random.nextInt(2);
            if (success == 0) {
                System.out.println("Успешно");
                gameService.shot(gameId, shooter, target);
            } else {
                System.out.println("Промах");
            }
            String temp = shooter;
            shooter = target;
            target = temp;
            i1++;
        }
        StatisticDto statistic = gameService.finishGame(gameId);
        System.out.println(statistic);
        first = scanner.nextLine();
        second = scanner.nextLine();
        gameId = gameService.startGame("127.0.0.1", "127.0.0.2", first, second);
        shooter = first;
        target = second;
        int i2 = 0;
        while (i2 < 10){
            System.out.println(shooter + " делайте выстрел в "+ target);
            scanner.nextLine();
            int success = random.nextInt(2);
            if(success == 0){
                System.out.println("Успешно");
                gameService.shot(gameId, shooter, target);
            }else {
                System.out.println("Промах");
            }
            String temp = shooter;
            shooter = target;
            target = temp;
            i2++;
        }
        statistic = gameService.finishGame(gameId);
        System.out.println(statistic);

        first = scanner.nextLine();
        second = scanner.nextLine();
        gameId = gameService.startGame("127.0.0.1", "127.0.0.2", first, second);
        shooter = first;
        target = second;
        int i3 = 0;
        while (i3 < 10){
            System.out.println(shooter + " делайте выстрел в "+ target);
            scanner.nextLine();
            int success = random.nextInt(2);
            if(success == 0){
                System.out.println("Успешно");
                gameService.shot(gameId, shooter, target);
            }else {
                System.out.println("Промах");
            }
            String temp = shooter;
            shooter = target;
            target = temp;
            i3++;
        }
        statistic = gameService.finishGame(gameId);
        System.out.println(statistic);
        int x = 100;
    }
}
