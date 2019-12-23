package Environment;

import Utils.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MiningField {

    public static final int MAP_HEIGHT = 20;
    public static final int MAP_WIDTH = 20;

    @Setter
    @Getter
    private int goldPieces;
    @Getter
    @Setter
    private int obstacles;
    @Getter
    @Setter
    private char[][] map;

    public MiningField() {
        this.goldPieces = 30;
        this.obstacles = 30;
        map = new char[MAP_HEIGHT][MAP_WIDTH];
        initMiningField();
    }

    public MiningField(int goldPieces, int obstacles) {
        this.obstacles = obstacles;
        this.goldPieces = goldPieces;
        map = new char[MAP_HEIGHT][MAP_WIDTH];
        initMiningField();
    }

    private void initMiningField() {
        fillMiningField();
        addGold(goldPieces);
        addObstacles(obstacles);
    }

    private void fillMiningField() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            Arrays.fill(this.map[i], '*');
        }
    }

    private void addGold(int goldPieces) {
        int i = 0;
        while (i < goldPieces) {
            Position position = new Position(generateRandom(), generateRandom());
            if (isFree(position)) {
                i++;
                this.map[position.getRow()][position.getCol()] = 'O';
            }
        }
    }

    private void addObstacles(int obstacles) {
        int i = 0;
        while (i < obstacles) {
            Position position = new Position(generateRandom(), generateRandom());
            if (isFree(position)) {
                i++;
                this.map[position.getRow()][position.getCol()] = 'X';
            }
        }
    }

    private boolean isFree(Position position) {
        return this.map[position.getRow()][position.getCol()] == '*';
    }

    private int generateRandom() {
        return (int) (Math.random()*((MAP_HEIGHT - 1) + 1));
    }

}
