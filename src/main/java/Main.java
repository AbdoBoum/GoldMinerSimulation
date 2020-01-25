import Agents.Leader;

public class Main {
    public static void main(String[] args) {

    }

    private static void printScores(Leader leader) {
        for (int i = 0; i < 4; i++)
            System.out.println("Score: " + leader.getMinerByIndex(i).getScore() + " Winner: " + leader.getMinerByIndex(i).isWinner());
    }


}
