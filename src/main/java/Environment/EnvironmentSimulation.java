package Environment;

import Agents.Leader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static Simulator.Event.*;

/**
 * Une instance de cette classe permet de faire simuler une environment
 */
@Getter
@Setter
public class EnvironmentSimulation {

    private MiningField field;

    public EnvironmentSimulation(){
        field = new MiningField();
    }

    public static void simulerConfigurationParDefault(){
        EnvironmentSimulation env = new EnvironmentSimulation();
        MiningField field= env.getField();
        Leader leader = field.getOwner();
        List<Position> positions = generateRandomPositions();
        while (field.getGoldPieces()>15) {
            for (int i = 0; i < leader.getMiners().size(); i++) {
                //genere une position aleatoire
                searchInPosition(leader.getMinerByIndex(i), field, positions.get(i));
            }

            for (int i = 0; i < leader.getMiners().size(); i++) {
                if (leader.getMinerByIndex(i).getPosition().equals(new Position()) &&
                        !leader.getMinerByIndex(i).isFree()) {
                    dropGold(leader.getMinerByIndex(i), field);
                }
            }
        }
        System.out.println("Gold: " +field.getGoldPieces());


    }

    public static void simulatWithOneMiner(){
       EnvironmentSimulation env  = new EnvironmentSimulation();
       MiningField field = env.getField();
       Leader leader = field.getOwner();
        while (field.getGoldPieces()!=0) {
                Position position = generateRandomPosition();
                searchInPosition(leader.getMinerByIndex(0), field, position);
                if (leader.getMinerByIndex(0).getPosition().equals(new Position()) &&
                        !leader.getMinerByIndex(0).isFree()) {
                    dropGold(leader.getMinerByIndex(0), field);
                }

        }
        System.out.println("Gold: " +field.getGoldPieces());
        System.out.println("Score: " +leader.getMinerByIndex(0).getScore());
    }

    private static void printScores(Leader leader) {
        for (int i = 0; i < 4; i++)
        System.out.println("Score: " + leader.getMinerByIndex(i).getScore()
                + " Winner: " + leader.getMinerByIndex(i).isWinner());
    }

}
