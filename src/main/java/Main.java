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
        while (field.getGoldPieces() > 15) {
            var position = new Position(RandomGenerator.generateRandom(MAP_HEIGHT), RandomGenerator.generateRandom(MAP_HEIGHT));
            System.out.println("Position to: " + position);
            leader.getMinerByIndex(0).searchInPosition(field, position);
            if (leader.getMinerByIndex(0).getPosition().equals(new Position())) {
                System.out.println("Here");
                leader.getMinerByIndex(0).dropGold(field);
            }
        }
        System.out.println(field.getGoldPieces());
        System.out.println(field.getObstacles());
        for (int i = 0;  i<4; i++)
        System.out.println("Score: " + leader.getMinerByIndex(i).getScore() + " Winner: " + leader.getMinerByIndex(0).isWinner());
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
