package Agents;

import Environment.MiningField;
import Utils.Position;
import lombok.*;

import java.util.UUID;

import static Agents.Agent.Type.GOLD_DROPPED;

@Getter @Setter @AllArgsConstructor
public class Miner implements Agent {

    private String id;
    private boolean free;
    private Position position;
    private int score;
    private boolean winner;
    private Position direction;

    public Miner() {
        this(UUID.randomUUID().toString(), true, new Position(), 0, false, new Position());
    }

    public void moveUp() {

    }

    @Override
    public void broadcast(Type type, Object content) {

    }

    public void pickGold(MiningField field) {
      this.free = false;
      field.freePosition(position);
      this.direction = new Position(); //default position is the depot location
    }

    public void dropGold(Leader leader) {
        this.free = true;
        send(leader, GOLD_DROPPED);
    }

    @Override
    public void send(Agent to, Type type) {
        switch(type) {
            case ANNOUNCE_WINNER:
                break;
            case GOLD_DROPPED:
                Leader leader = (Leader)to;
                leader.updateScore(this);
                break;
        }
    }

    @Override
    public String toString() {
        return "---> Miner: " + this.id + " \n---> Position: " + position.toString();
    }


}
