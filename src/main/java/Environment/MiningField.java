package Environment;

import Agents.Leader;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.IntStream;

import static Simulator.RandomGenerator.generateRandom;

/**
 * The class Represent the Mining field where the agents will mining the golds
 */
public class MiningField {

    public static final int MAP_HEIGHT = 20;
    public static final int MAP_WIDTH = 20;
    public static final int DEFAULT_OBSTACLES = 15;
    public static final int DEFAULT_GOLD_PIECES = 25;

    /**
     * Represent the number of gold pieces in the field
     */
    @Setter @Getter
    private int goldPieces;
    /**
     * Number of obstacles in the mining field
     */
    @Getter @Setter
    private int obstacles;
    /**
     * Table that represent the field map
     */
    @Getter @Setter
    private char[][] map;
    /**
     * The leader of the mining field
     */
    @Getter
    private Leader owner;

    /**
     * Default constructor
     */
    public MiningField() {
        this(DEFAULT_GOLD_PIECES, DEFAULT_OBSTACLES, new Leader());
    }

    /**
     * Constructor who take number of golds, obstacles and the owner agent
     * @param goldPieces number of golds
     * @param obstacles number of obstacles
     * @param owner reference of the owner agent
     */
    public MiningField(int goldPieces, int obstacles, Leader owner) {
        this.obstacles = obstacles;
        this.goldPieces = goldPieces;
        map = new char[MAP_HEIGHT][MAP_WIDTH];
        this.owner = owner;
        initMiningField();
    }

    /**
     * Initialise the mining field
     */
    private void initMiningField() {
        fillMiningField();
        addMiners();
        addGold();
        addObstacles();
    }

    /**
     * fill the mining field
     */
    private void fillMiningField() {
        for (var i = 0; i < MAP_HEIGHT; i++) {
            Arrays.fill(this.map[i], '*');
        }
        map[0][0] = 'D';
        map[0][MAP_WIDTH-1]= 'D';
        map[MAP_HEIGHT-1][0] = 'D';
        map[MAP_HEIGHT-1][MAP_WIDTH-1] = 'D';
    }

    /**
     * Add miners the mining field
     */
    private void addMiners() {
        for (int i = 0; i < owner.getMiners().size(); i++) {
            var row = owner.getMinerByIndex(i).getPosition().getRow();
            var col = owner.getMinerByIndex(i).getPosition().getCol();
            this.map[row][col] = 'M';
        }
    }

    /**
     * Add golds in the mining field
     */
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

    /**
     * Add obstacles in the mining field
     */
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

    /**
     * Check if a position is free
     * @param position the position to check
     * @return boolean describe a position
     */
    public boolean isFreePosition(Position position) {
        return this.getMap()[position.getRow()][position.getCol()] == '*';
    }

    /**
     * Check if a position contain a gold
     * @param position the position to verify
     * @return boolean describe founding of gold
     */
    public boolean isGold(Position position) { return this.getMap()[position.getRow()][position.getCol()] == 'o'; }

    /**
     * Take a gold from a position
     */
    public void freePositionFromGold() {
        this.goldPieces--;
    }

    /**
     * Get free a position
     * @param position position to get free
     */
    public void freePosition(Position position) {
        this.getMap()[position.getRow()][position.getCol()] = '*';
    }

    /**
     * Set the miner in a position
     * @param position the position
     */
    public void setMinerInPosition(Position position) {
        this.getMap()[position.getRow()][position.getCol()] = 'M';
    }

    /**
     * Describe the mining field
     * @return string of description
     */
    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var i = 0; i < map.length; i++) {
            var line = map[i];
            IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .forEach(builder::append);
            builder.append('\n');
        }
        return builder.toString();
    }
}
