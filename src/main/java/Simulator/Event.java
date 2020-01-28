package Simulator;

import Agents.Miner;
import Environment.BfsShortestPath;
import Environment.MiningField;
import Environment.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static Agents.Agent.Type.GOLD_DROPPED;
import static Agents.Agent.Type.GOLD_FOUND;
import static Agents.Leader.NUM_MINERS;
import static Environment.BfsShortestPath.getPath;
import static Environment.MiningField.MAP_HEIGHT;
import static Environment.MiningField.MAP_WIDTH;

public class Event {

    public static List<Position> generateRandomPositions() {
        var positions = new ArrayList<Position>();
        for (var i = 0; i < NUM_MINERS; i++) {
            positions.add(generateRandomPosition());
        }
        return positions;
    }

    public static Position generateRandomPosition() {
        var row = RandomGenerator.generateRandom(MAP_HEIGHT);
        var col = RandomGenerator.generateRandom(MAP_WIDTH);
        return new Position(row, col);
    }

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

    public static void pickGold(Miner miner, MiningField field) {
        field.freePositionFromGold();
        miner.setFree(false);
        backToDeposit(miner, field);
    }

    private static void backToDeposit(Miner miner, MiningField field) {
        int minerIndex = field.getOwner().getMiners().indexOf(miner);
        miner.setDestination(field.getOwner().getDiposites().get(minerIndex));
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

    public static void dropGold(Miner miner, MiningField field){
        miner.send(GOLD_DROPPED, field);
        miner.setFree(true);
    }


}
