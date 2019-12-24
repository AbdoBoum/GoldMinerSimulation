package Agents;

import Utils.Position;
import lombok.*;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class Miner implements Agent {

    private String id;
    private boolean free;
    private Position position;
    private int score;
    private boolean winner;

    public Miner() {
        this(UUID.randomUUID().toString(), true, new Position(), 0, false);
    }

    public void moveUp() {

    }

    @Override
    public void broadcast(Type type, Object content) {

    }

    @Override
    public void send(Agent to, Type type) {

    }

    @Override
    public String toString() {
        return "---> Miner: " + this.id + " \n---> Position: " + position.toString();
    }

}
