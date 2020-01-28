import Agents.Leader;
import Environment.MiningField;
import Environment.Position;
import lombok.Getter;
import lombok.Setter;

import static Simulator.Event.*;

/**
 * Une instance de cette classe permet de faire simuler une environment
 */
@Getter
@Setter
public class StartSimulationFacade {

    private MiningField field;

    public StartSimulationFacade() {
        field = new MiningField();
    }

    public static void startSimulation() {
        var env = new StartSimulationFacade();
        var field = env.getField();
        var leader = field.getOwner();
        while (field.getGoldPieces() != 0) {
            var positions = generateRandomPositions();
            for (var i = 0; i < leader.getMiners().size(); i++) {
                searchInPosition(leader.getMinerByIndex(i), field, positions.get(i));
            }

            for (var i = 0; i < leader.getMiners().size(); i++) {
                if (leader.getMinerByIndex(i).getPosition().equals(leader.getDiposites().get(i)) &&
                        !leader.getMinerByIndex(i).isFree()) {
                    dropGold(leader.getMinerByIndex(i), field);
                }
            }
        }
        System.out.println("Gold: " + field.getGoldPieces());
        printScores(leader);
    }

    public static void simulateOneMiner() {
        var env = new StartSimulationFacade();
        var field = env.getField();
        var leader = field.getOwner();
        while (field.getGoldPieces() != 0) {
            Position position = generateRandomPosition();
            searchInPosition(leader.getMinerByIndex(0), field, position);
            if (leader.getMinerByIndex(0).getPosition().equals(new Position()) &&
                    !leader.getMinerByIndex(0).isFree()) {
                dropGold(leader.getMinerByIndex(0), field);
            }

        }
        System.out.println("Gold: " + field.getGoldPieces());
        System.out.println("Score: " + leader.getMinerByIndex(0).getScore());
    }

    private static void printScores(Leader leader) {
        for (var i = 0; i < 4; i++)
            System.out.println("Score: " + leader.getMinerByIndex(i).getScore()
                    + " Winner: " + leader.getMinerByIndex(i).isWinner());
    }

}
