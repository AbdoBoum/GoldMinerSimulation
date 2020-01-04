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
        while (field.getGoldPieces() > 25) {
            var positions = generateRandomPositions();
            for (int i = 0; i < leader.getMiners().size(); i++) {
                leader.getMinerByIndex(i).searchInPosition(field, positions.get(i));
            }
            for (int i = 0; i < leader.getMiners().size(); i++) {
                if (leader.getMinerByIndex(i).getPosition().equals(new Position())) {
                    leader.getMinerByIndex(i).dropGold(field);
                }
            }
        }
        for (int i = 0; i < leader.getMiners().size(); i++) {
            System.out.println("Miner: " + i + ". Score: " + leader.getMinerByIndex(i).getScore() + " Winner: " + leader.getMinerByIndex(i).isWinner());
        }
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
