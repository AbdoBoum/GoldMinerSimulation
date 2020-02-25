package main;

import Agents.Leader;
import Environment.MiningField;
import Environment.Position;
import lombok.Getter;
import lombok.Setter;

import static Simulator.Event.*;

/**
 * Instance of this class allow to lunch some simulation
 * the role of the class is to be a facade of simulations
 */
@Getter
@Setter
public class StartSimulationFacade {

    private MiningField field;

    /**
     * Default Constructor who initiate the mining field
     */
    public StartSimulationFacade() {
        field = new MiningField();
    }

    /**
     * Method allow to simulate a normal event
     */
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

    /**
     * Method that simulate event with one Miner
     */
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

    /**
     * Print the scores of the miners
     * @param leader the leader of miners
     */
    private static void printScores(Leader leader) {
        for (var i = 0; i < 4; i++)
            System.out.println("Score: " + leader.getMinerByIndex(i).getScore()
                    + " Winner: " + leader.getMinerByIndex(i).isWinner());
    }

}
