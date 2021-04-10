package ru.inno.game.app;

import ru.inno.game.dto.StatisticDto;
import ru.inno.game.repository.*;
import ru.inno.game.services.GameService;
import ru.inno.game.services.GameServiceImpl;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PlayersRepository playersRepository = new PlayersRepositoryFilesImpl();
        GamesRepository gamesRepository = new GamesRepositoryListImpl();
        ShotsRepository shotsRepository = new ShotsRepositoryListImpl();
        GameService gameService = new GameServiceImpl(playersRepository, gamesRepository, shotsRepository);
        Scanner scanner = new Scanner(System.in);
        String first = scanner.nextLine();
        String second = scanner.nextLine();
        Random random = new Random();
        Long gameId = gameService.startGame("127.0.0.1", "127.0.0.2", first, second);
        String shooter = first;
        String target = second;
        int i1 = 0;
        while (i1 < 10){
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
            i1++;
        }
        StatisticDto statistic = gameService.finishGame(gameId);
        System.out.println(statistic);

/*
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
        System.out.println(statistic);*/
        int x = 100;
    }
}
