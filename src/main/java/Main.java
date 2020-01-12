import Agents.Leader;
import Environment.MiningField;
import Utils.Position;
import Utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

import static Environment.MiningField.MAP_HEIGHT;

public class Main {
    public static void main(String[] args) {
        var field = new MiningField();
        var leader = field.getOwner();
        while (field.getGoldPieces() > 18) {
            var positions = generateRandomPositions();
            for (var i = 0; i < 4; i++) {
                leader.getMinerByIndex(i).searchInPosition(field, positions.get(i));
            }
            for (var i = 0; i < 4; i++) {
                if (leader.getMinerByIndex(i).getPosition().equals(new Position()) && !leader.getMinerByIndex(i).isFree()) {
                    leader.getMinerByIndex(i).dropGold(field);
                }
            }
        }
        System.out.println("Gold: " + field.getGoldPieces());
        System.out.println("Obstacles: " + field.getObstacles());
        printScores(leader);
    }

    private static void printScores(Leader leader) {
        for (int i = 0; i < 4; i++)
            System.out.println("Score: " + leader.getMinerByIndex(i).getScore() + " Winner: " + leader.getMinerByIndex(i).isWinner());

    }

    private static List<Position> generateRandomPositions() {
        var positions = new ArrayList<Position>();
        int maxRandomValue = RandomGenerator.generateRandom(MAP_HEIGHT);
        for (int i = 0; i < 4; i++) {
            Position position = new Position(maxRandomValue, maxRandomValue);
            positions.add(position);
        }
        return positions;
    }
}
