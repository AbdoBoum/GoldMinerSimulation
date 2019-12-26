package Agents;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Leader implements Agent{

    private static final int NUM_MINERS = 4;
    @Getter private List<Agent> miners;

    public Leader() {
        miners = new ArrayList<>();
        for (int i = 0; i < NUM_MINERS; i++) {
            miners.add(new Miner());
        }
    }

    public Miner getMinerByIndex(int index) {
        return (Miner)this.miners.get(index);
    }

    public void updateScore(Miner miner) {
        miner.setScore(miner.getScore() + 1);
    }

    @Override
    public void broadcast(Type type, Object content) {
        switch (type) {
            case ANNOUNCE_WINNER:
                updateWinner((Miner)content);
                break;
            default: return;
        }
    }

    private void updateWinner(Miner winner) {
        for (int i = 0; i < NUM_MINERS; i++) {
            if (getMinerByIndex(i).equals(winner))
                winner.setWinner(true);
            else getMinerByIndex(i).setWinner(false);
        }
    }

    @Override
    public void send(Agent to, Type type) {

    }
}
