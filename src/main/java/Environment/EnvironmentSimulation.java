package Environment;

import Agents.Leader;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        //TODO DELETE BUFFER
        BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
        EnvironmentSimulation env = new EnvironmentSimulation();
        MiningField field= env.getField();
        Leader leader = field.getOwner();
        while (field.getGoldPieces()>0) {
            List<Position> positions = generateRandomPositions();
            for (int i = 0; i < leader.getMiners().size(); i++) {
                searchInPosition(leader.getMinerByIndex(i), field, positions.get(i));

//                try {
//                    brr.readLine();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }

            for (int i = 0; i < leader.getMiners().size(); i++) {
                if (leader.getMinerByIndex(i).getPosition().equals(leader.getMinerByIndex(i).getDeposite()) &&
                        !leader.getMinerByIndex(i).isFree()) {
                    dropGold(leader.getMinerByIndex(i), field);
                }
            }
            for (int i = 0; i < leader.getMiners().size(); i++) {
                System.out.println(leader.getMinerByIndex(i).getScore());
            }
            System.out.println("The field----------------");
            System.out.println(field);
//            try {
//                brr.readLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("Gold: " +field.getGoldPieces());

        printScores(leader);

    }

    public static void simulatWithOneMiner(){
       EnvironmentSimulation env  = new EnvironmentSimulation();
       MiningField field = env.getField();
       Leader leader = field.getOwner();
        while (field.getGoldPieces()!=0) {
                Position position = generateRandomPosition();
                searchInPosition(leader.getMinerByIndex(1), field, position);
                if (leader.getMinerByIndex(1).getPosition().equals(leader.getMinerByIndex(1).getDeposite()) &&
                        !leader.getMinerByIndex(1).isFree()) {
                    dropGold(leader.getMinerByIndex(1), field);
                }

        }
        System.out.println("Gold: " +field.getGoldPieces());
        System.out.println("Score: " +leader.getMinerByIndex(1).getScore());
    }

    private static void printScores(Leader leader) {
        for (int i = 0; i < 4; i++)
        System.out.println("Score: " + leader.getMinerByIndex(i).getScore()
                + " Winner: " + leader.getMinerByIndex(i).isWinner());
    }

}
