package Agents;

import Environment.MiningField;
import Environment.Position;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Agents.Agent.Type.ANNOUNCE_WINNER;
import static Agents.Agent.Type.GOLD_POSITION;
import static Environment.MiningField.MAP_HEIGHT;
import static Environment.MiningField.MAP_WIDTH;
import static Simulator.Event.searchInPosition;
import static Simulator.RandomGenerator.generateRandom;

/**
 * this class implement Agent interface, the class represent
 * the Leader agent who is the responsible of the mining field
 * @author Boumahdi, El haddaoui
 */
public class Leader implements Agent{

    public static final int NUM_MINERS = 4;
    /**
     * Collection of miners that has the current leader
     */
    @Getter private List<Agent> miners;
    /**
     * Represent the team maximum score
     */
    @Getter private int teamMaxScore;
    /**
     * Positions of the deposit
     */
    @Getter
    private List<Position> diposites;

    /**
     * the default constructor
     */
    public Leader() {
        teamMaxScore = 0;
        createDiposites();
        createMiners();
        generateMinersInitialPosition();
    }

    /**
     * Create the deposits for a mining field
     */
    private void createDiposites(){
        diposites = new ArrayList<>();
        diposites.add(new Position());
        diposites.add(new Position(MAP_HEIGHT - 1, 0));
        diposites.add(new Position(0, MAP_WIDTH - 1));
        diposites.add(new Position(MAP_HEIGHT - 1, MAP_WIDTH - 1));
    }

    /**
     * Create agents miners for the current leader
     */
    private void createMiners() {
        miners = new ArrayList<>();
        for (int i = 0; i < NUM_MINERS; i++) {
            miners.add(new Miner());
        }
    }

    /**
     * Generate positions for the miners
     */
    private void generateMinersInitialPosition() {
        Set<Position> positions = new HashSet<>();
        int i = 0;
        while (i < NUM_MINERS) {
            Position position = new Position(generateRandom(MAP_HEIGHT), generateRandom(MAP_WIDTH));
            if (!positions.contains(position)) {
                positions.add(position);
                this.getMinerByIndex(i++).setPosition(position);
            }
        }

    }

    /**
     * Used to get the agent miner by his index
     * @param index the index
     * @return miner object
     */
    public Miner getMinerByIndex(int index) {
        return (Miner) this.miners.get(index);
    }

    /**
     * Function used to get the index of the miner agent
     * @param miner the miner
     * @return the index of the miner
     */
    public int getMinerIndex(Miner miner) {
        return miners.indexOf(miner);
    }

    /**
     * Update the score of a specified miner
     * @param miner miner object
     */
    public void updateScore(Miner miner) {
        miner.setScore(miner.getScore() + 1);
        if (miner.getScore() > teamMaxScore) {
            teamMaxScore = miner.getScore();
            broadcast(ANNOUNCE_WINNER, miner);
        }
    }

    @Override
    public void broadcast(Type type, Object content) {
        switch (type) {
            case ANNOUNCE_WINNER:
                updateWinner((Miner) content);
                break;
            default:
                break;
        }
    }

    /**
     * Update the miner winner
     * @param winner miner Object
     */
    private void updateWinner(Miner winner) {
        for (int i = 0; i < NUM_MINERS; i++) {
            if (getMinerByIndex(i).equals(winner))
                getMinerByIndex(i).setWinner(true);
            else getMinerByIndex(i).setWinner(false);
        }
    }

    @Override
    public void send(Type type, Object... content) {
        switch (type) {
            case ANNOUNCE_WINNER:
            case GOLD_DROPPED:
            case GOLD_FOUND:
                break;
            case GOLD_POSITION:
                Miner miner = (Miner) content[0];
                MiningField field = (MiningField) content[1];
                Position goldPosition = (Position) content[2];
                searchInPosition(miner, field, goldPosition);
                break;
        }
    }

    /**
     * Affect to miner a position of gold to search
     * @param field Mining field position
     * @param goldPosition Position of a gold
     */
    public void affectMinerToGold(MiningField field, Position goldPosition) {
        if (this.areAllMinersBusy()) return;
        int random = generateRandom(NUM_MINERS);
        if (getMinerByIndex(random).isFree()) {
            send(GOLD_POSITION, getMinerByIndex(random), field, goldPosition);
        } else {
            affectMinerToGold(field, goldPosition);
        }
    }

    /**
     * Lock if the miners are busy
     * @return boolean
     */
    public boolean areAllMinersBusy() {
        for (int i = 0; i < miners.size(); i++) {
            if (getMinerByIndex(i).isFree()) return false;
        }
        return true;
    }
}
