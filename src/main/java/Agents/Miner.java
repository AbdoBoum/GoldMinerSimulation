package Agents;

import Utils.Position;
import lombok.*;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class Miner {

    private String id;
    private boolean free;
    private Position position;

    public Miner() {
        this.id = UUID.randomUUID().toString();
        this.free = true;
        this.position = new Position() ;
    }

    public void moveUp() {

    }

    public void moveDown() {

    }

    public void moveLeft() {

    }

    public void moveRight() {

    }

    @Override
    public String toString() {
        return "---> Miner: " + this.id + " \n---> Position: " + position.toString();
    }
}
