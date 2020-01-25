package Environment;

import Agents.Leader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.IntStream;

import static Simulator.RandomGenerator.generateRandom;

public class MiningField {

    public static final int MAP_HEIGHT = 20;
    public static final int MAP_WIDTH = 20;
    public static final int DEFAULT_OBSTACLES = 15;
    public static final int DEFAULT_GOLD_PIECES = 20;

    @Setter @Getter
    private int goldPieces;
    @Getter @Setter
    private int obstacles;
    @Getter @Setter
    private char[][] map;
    @Getter
    private Leader owner;

    public MiningField() {
        this(DEFAULT_GOLD_PIECES, DEFAULT_OBSTACLES, new Leader());
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
        addGold();
        addObstacles();
    }

    private void fillMiningField() {
        for (var i = 0; i < MAP_HEIGHT; i++) {
            Arrays.fill(this.map[i], '*');
        }
        map[0][0] = 'D';
    }

    private void addMiners() {
        for (int i = 0; i < owner.getMiners().size(); i++) {
            var row = owner.getMinerByIndex(i).getPosition().getRow();
            var col = owner.getMinerByIndex(i).getPosition().getCol();
            this.map[row][col] = 'M';
        }
    }

    private void addGold() {
        var i = 0;
        while (i < this.goldPieces) {
            Position position = new Position(generateRandom(MAP_HEIGHT), generateRandom(MAP_WIDTH));
            if (isFreePosition(position)) {
                this.map[position.getRow()][position.getCol()] = 'o';
                i++;
            }
        }
    }

    private void addObstacles() {
        var i = 0;
        while (i < this.obstacles) {
            Position position = new Position(generateRandom(MAP_HEIGHT), generateRandom(MAP_WIDTH));
            if (isFreePosition(position)) {
                this.map[position.getRow()][position.getCol()] = '#';
                i++;
            }
        }
    }

    public boolean isFreePosition(Position position) {
        return this.map[position.getRow()][position.getCol()] == '*';
    }

    public boolean isGold(Position position) { return this.map[position.getRow()][position.getCol()] == 'o'; }

    public void freePositionFromGold() {
        this.goldPieces--;
    }

    public void freePosition(Position position) {
        this.map[position.getRow()][position.getCol()] = '*';
    }

    public void setMinerInPosition(Position position) {
        this.map[position.getRow()][position.getCol()] = 'M';
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var i = 0; i < MAP_WIDTH; i++) {
            var line = map[i];
            IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .forEach(builder::append);
            builder.append('\n');
        }
        return builder.toString();
    }
}
