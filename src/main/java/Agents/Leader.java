package Agents;

import Environment.MiningField;
import Utils.Position;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Agents.Agent.Type.ANNOUNCE_WINNER;
import static Agents.Agent.Type.GOLD_POSITION;
import static Environment.MiningField.MAP_HEIGHT;
import static Environment.MiningField.MAP_WIDTH;
import static Utils.RandomGenerator.generateRandom;

public class Leader implements Agent{

    private static final int NUM_MINERS = 4;
    @Getter private List<Agent> miners;
    @Getter private int teamMaxScore;

    public Leader() {
        teamMaxScore = 0;
        miners = new ArrayList<>();
        createMiners();
        generateMinersInitialPosition();
    }

    private void createMiners() {
        for (int i = 0; i < NUM_MINERS; i++) {
            miners.add(new Miner());
        }
    }

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

    public Miner getMinerByIndex(int index) {
        return (Miner)this.miners.get(index);
    }

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
                updateWinner((Miner)content);
                break;
            default: break;
        }
    }

    private void updateWinner(Miner winner) {
        for (int i = 0; i < NUM_MINERS; i++) {
            if (getMinerByIndex(i).equals(winner))
                winner.setWinner(true);
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
                Miner miner = (Miner)content[0];
                MiningField field = (MiningField)content[1];
                Position goldPosition = (Position)content[2];
                miner.searchInPosition(field, goldPosition);
                break;
        }
    }

    public void affectMinerToGold(MiningField field, Position goldPosition) {
        if (this.areAllMinersBusy()) return;
        int random = generateRandom(4);
        if (this.getMinerByIndex(random).isFree()) {
            this.send(GOLD_POSITION, this.getMinerByIndex(random), field, goldPosition);
        } else {
            affectMinerToGold(field, goldPosition);
        }
    }

    public boolean areAllMinersBusy() {
        for (int i = 0; i < miners.size(); i++) {
            if (this.getMinerByIndex(i).isFree()) return false;
        }
        return true;
    }
}
