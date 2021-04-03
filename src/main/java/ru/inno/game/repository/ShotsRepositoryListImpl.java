package ru.inno.game.repository;

import lombok.AllArgsConstructor;
import ru.inno.game.models.Shot;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class ShotsRepositoryListImpl implements ShotsRepository {
    private List<Shot> shots;
    public ShotsRepositoryListImpl(){
        shots = new ArrayList<>();
    }
    @Override
    public void save(Shot shot) {
        this.shots.add(shot);
    }
}
