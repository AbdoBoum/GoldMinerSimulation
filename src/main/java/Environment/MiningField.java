package Environment;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MiningField {

    private static final int MAP_HEIGHT = 20;
    private static final int MAP_WIDTH = 20;

    @Setter @Getter
    private int goldPieces;
    @Getter @Setter
    private int obstacles;
    @Getter @Setter
    private char[][] map;

    public MiningField() {
        new MiningField(30, 30);
    }

    public MiningField(int goldPieces, int obstacles) {
        initMiningField();
        addGold(goldPieces);
        addObstacles(obstacles);
    }

    private void initMiningField() {
        map = new char[MAP_HEIGHT][MAP_WIDTH];
        for (int i = 0; i < MAP_HEIGHT; i++) {
            Arrays.fill(map[i], '*');
        }
    }

    private void addGold(int goldPieces) {

    }

    private void addObstacles(int obstacles) {

    }

    private Set<Integer> generateGoldLocation(int goldPieces) {
        var goldLocation = new HashSet<Integer>();
        int i = 0;
        while(i < goldPieces) {
            i++;
        }
        return goldLocation;
    }

    private int generateRandom() {
        return 0;
    }

}
