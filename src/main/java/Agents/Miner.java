package Agents;

import Environment.MiningField;
import Environment.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * This class represent the agent miner
 * @author Boumahdi
 */
@Getter @Setter @AllArgsConstructor
public class Miner implements Agent {
    /**
     * The identifier of the miner agent
     */
    private String id;
    /**
     * To represent the availability of agent
     */
    private boolean free;
    /**
     * Position of miner
     */
    private Position position;
    /**
     * The Score
     */
    private int score;
    /**
     * To represent the winner
     */
    private boolean winner;
    /**
     * the position of the destination
     */
    private Position destination;

    /**
     * default constructor of miner
     */
    public Miner(){
        this(UUID.randomUUID().toString(), true, new Position(), 0, false, new Position());
    }

    @Override
    public void broadcast(Type type, Object content) {
        throw new IllegalArgumentException("Miner can't broadcast");
    }

    @Override
    public void send(Type type, Object... content) {
        MiningField field = (MiningField)content[0];
        switch(type) {
            case ANNOUNCE_WINNER:
                break;
            case GOLD_DROPPED:
                field.getOwner().updateScore(this);
                break;
            case GOLD_FOUND:
                field.getOwner().affectMinerToGold(field ,this.getPosition());
        }
    }

    /**
     * Generate the description of the miner
     * @return String that describe the miner
     */
    @Override
    public String toString() {
        return "---> Miner: " + this.id + " \n---> Position: " + position.toString();
    }

}