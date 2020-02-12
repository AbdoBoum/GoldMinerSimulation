package Simulator;

import Agents.Miner;
import Environment.MiningField;
import Environment.Position;

import java.util.ArrayList;
import java.util.List;

import static Agents.Agent.Type.GOLD_DROPPED;
import static Agents.Agent.Type.GOLD_FOUND;
import static Agents.Leader.NUM_MINERS;
import static Environment.BfsShortestPath.getPath;
import static Environment.MiningField.MAP_HEIGHT;
import static Environment.MiningField.MAP_WIDTH;

/**
 * The class deal with the events that happen in the environment and the agents
 */
public class Event {

    /**
     * Create collection of positions in the fields
     * @return list of Position
     */
    public static List<Position> generateRandomPositions() {
        var positions = new ArrayList<Position>();
        for (var i = 0; i < NUM_MINERS; i++) {
            positions.add(generateRandomPosition());
        }
        return positions;
    }

    /**
     * Generate a random position
     * @return position
     */
    public static Position generateRandomPosition() {
        var row = RandomGenerator.generateRandom(MAP_HEIGHT);
        var col = RandomGenerator.generateRandom(MAP_WIDTH);
        return new Position(row, col);
    }

    /**
     * the method allow to the miner to go in position of field and
     * search in every place that he passe by
     * @param miner the miner who want to move
     * @param field the field
     * @param destination the destination to go
     */
    public static void searchInPosition(Miner miner, MiningField field, Position destination) {
        if (!miner.isFree()) {
            backToDeposit(miner, field);
            return;
        }
        var path = getPath(miner.getPosition(), destination,field.getMap());
        for (var i = 1; i < path.size(); i++) {
            System.out.println(miner.getId() + " " + miner.getPosition());
            System.out.println(field.toString());
            field.freePosition(miner.getPosition());
            miner.setPosition(path.get(i));
            if (field.isGold(miner.getPosition())) {
                field.setMinerInPosition(miner.getPosition());
                pickGold(miner, field);
                break;
            } else {
                field.setMinerInPosition(miner.getPosition());
            }
        }
        System.out.println("the miner is finish her role"+miner.getId());
    }

    /**
     * Method handle the pick of Gold event
     * @param miner miner that want to pick a gold
     * @param field the field
     */
    public static void pickGold(Miner miner, MiningField field) {
        field.freePositionFromGold();
        miner.setFree(false);
        backToDeposit(miner, field);
    }

    /**
     * The method allow to the miner to get back to the deposit and trigger the event of finding
     * an other gold
     * @param miner the miner who want to back to deposit
     * @param field the field where he work on
     */
    private static void backToDeposit(Miner miner, MiningField field) {
        int index = field.getOwner().getMinerIndex(miner);
        miner.setDestination(field.getOwner().getDiposites().get(index));
        var path = getPath(miner.getPosition(), miner.getDestination(),field.getMap());
        System.out.println("Back to deposit");
        for (var i = 1; i < path.size(); i++) {
            System.out.println(miner.getId() + " " + miner.getPosition());
            System.out.println(field.toString());
            if (!field.isGold(miner.getPosition())) field.freePosition(miner.getPosition());
            miner.setPosition(path.get(i));
            if (field.isGold(miner.getPosition())) {
                System.out.println("Found gold and I'm busy!!");
                miner.send(GOLD_FOUND, field);
            } else {
                field.setMinerInPosition(miner.getPosition());
            }
        }
        System.out.println(miner.getId() + " " + miner.getPosition());
        System.out.println(field.toString());
    }

    /**
     * This method handle the event of dropping the gold in the deposit
     * @param miner miner who want to drop the gold
     * @param field field to drop the gold
     */
    public static void dropGold(Miner miner, MiningField field){
        miner.send(GOLD_DROPPED, field);
        miner.setFree(true);
    }


}
