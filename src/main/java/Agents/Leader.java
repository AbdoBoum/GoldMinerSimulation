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

    public void updateScore(Miner miner) {
        miner.setScore(miner.getScore() + 1);
    }

    @Override
    public void broadcast(Type type, Object content) {
        switch (type) {
            case ANNOUNCE_WINNER:
                Miner miner = (Miner) miners.stream()
                        .filter(c -> c.equals((Miner)content))
                        .findFirst()
                        .get();
                miner.setWinner(true);
                break;
            default: return;
        }
    }

    @Override
    public void send(Agent to, Type type) {

    }
}
