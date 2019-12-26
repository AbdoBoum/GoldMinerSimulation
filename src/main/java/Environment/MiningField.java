package Environment;

import Agents.Leader;
import Utils.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.IntStream;

import static Utils.RandomGenerator.generateRandom;

public class MiningField {

    public static final int MAP_HEIGHT = 20;
    public static final int MAP_WIDTH = 20;

    @Setter @Getter
    private int goldPieces;
    @Getter @Setter
    private int obstacles;
    @Getter @Setter
    private char[][] map;
    @Getter
    private Leader owner;

    public MiningField() {
        this(30, 30, new Leader());
    }

    public MiningField(int goldPieces, int obstacles, Leader owner) {
        this.obstacles = obstacles;
        this.goldPieces = goldPieces;
        map = new char[MAP_HEIGHT][MAP_WIDTH];
        this.owner = owner;
        initMiningField();
    }

    private void initMiningField() {
        fillMiningField();
        addMiners();
        addGold(goldPieces);
        addObstacles(obstacles);
    }

    private void addMiners() {
        for (int i = 0; i < owner.getMiners().size(); i++) {
            int col = owner.getMinerByIndex(i).getPosition().getCol();
            int row = owner.getMinerByIndex(i).getPosition().getRow();
            this.map[col][row] = 'M';
        }
    }

    private void fillMiningField() {
        for (int i = 0; i < MAP_HEIGHT; i++) {
            Arrays.fill(this.map[i], '*');
        }
    }

    private void addGold(int goldPieces) {
        int i = 0;
        while (i < goldPieces) {
            Position position = new Position(generateRandom(MAP_HEIGHT), generateRandom(MAP_WIDTH));
            if (isFree(position)) {
                i++;
                this.map[position.getRow()][position.getCol()] = 'O';
            }
        }
    }

    private void addObstacles(int obstacles) {
        int i = 0;
        while (i < obstacles) {
            Position position = new Position(generateRandom(MAP_HEIGHT), generateRandom(MAP_WIDTH));
            if (isFree(position)) {
                i++;
                this.map[position.getRow()][position.getCol()] = 'X';
            }
        }
    }

    public boolean isFree(Position position) {
        return this.map[position.getRow()][position.getCol()] == '*';
    }

    public void freePosition(Position position) {
        this.map[position.getRow()][position.getCol()] = '*';
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAP_WIDTH; i++) {
            char[] line = map[i];
            IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .forEach(builder::append);
            builder.append('\n');
        }
        return builder.toString();
    }
}
