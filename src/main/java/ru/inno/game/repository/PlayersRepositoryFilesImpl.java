package ru.inno.game.repository;

import ru.inno.game.models.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PlayersRepositoryFilesImpl implements PlayersRepository {

    private String path;


    public PlayersRepositoryFilesImpl() {

        path = "src/main/resources/PlayerRepositoryFile.txt";
    }

    @Override
    public Player findByNickname(String nickname) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parsedLines = line.split("#");
                if (parsedLines[0].equals(nickname)) {
                    return new Player(parsedLines[0], parsedLines[1],
                            Integer.parseInt(parsedLines[2]), Integer.parseInt(parsedLines[3]), Integer.parseInt(parsedLines[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void save(Player player) {


        try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)))) {
            StringBuilder sb = new StringBuilder();
            sb.append(player.getName())
                    .append("#")
                    .append(player.getIp())
                    .append("#")
                    .append(player.getScore())
                    .append("#")
                    .append(player.getMaxWinsCount())
                    .append("#")
                    .append(player.getMinLosesCount())
                    .append("\n");
            br.write(String.valueOf(sb));
        } catch (IOException e) {
            System.err.println("Файл не найден в save()");
            e.printStackTrace();
            e.getMessage();
        }

    }

    @Override
    public void update(Player player) {
        List<Player> playerList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parsedLines = line.split("#");
                playerList.add(new Player(parsedLines[0], parsedLines[1],
                        Integer.parseInt(parsedLines[2]), Integer.parseInt(parsedLines[3]), Integer.parseInt(parsedLines[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getName().equals(player.getName())) {
                playerList.set(i, player);
                break;
            }
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {

            for (Player value : playerList) {
                StringBuilder sb = new StringBuilder();
                sb.append(value.getName())
                        .append("#")
                        .append(value.getIp())
                        .append("#")
                        .append(value.getScore())
                        .append("#")
                        .append(value.getMaxWinsCount())
                        .append("#")
                        .append(value.getMinLosesCount())
                        .append("\n");
                br.write(String.valueOf(sb));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
