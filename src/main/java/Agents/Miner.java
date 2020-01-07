package Agents;

import Environment.BfsShortestPath;
import Environment.MiningField;
import Utils.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static Agents.Agent.Type.GOLD_DROPPED;
import static Agents.Agent.Type.GOLD_FOUND;

@Getter @Setter @AllArgsConstructor
public class Miner implements Agent {

    private String id;
    private boolean free;
    private Position position;
    private int score;
    private boolean winner;
    private Position destination;

    public Miner() {
        this(UUID.randomUUID().toString(), true, new Position(), 0, false, new Position());
    }

    @Override
    public void broadcast(Type type, Object content) {
        throw new IllegalArgumentException("Miner can't broadcast");
    }

    public void searchInPosition(MiningField field, Position destination) {
        var bfs = new BfsShortestPath(field.getMap());
        var path = bfs.getPath(this.position, destination);
        for (var i = 1; i < path.size(); i++) {
            //System.out.println(position);
            //System.out.println(field.toString());
            field.freePosition(this.position);
            this.position = path.get(i);
            if (field.isGold(this.position)) {
                field.setMinerInPosition(this.position);
                pickGold(field);
                break;
            } else {
                field.setMinerInPosition(this.position);
            }
        }
    }

    public void pickGold(MiningField field) {
        field.freePositionFromGold(this.position);
        this.free = false;
        backToDeposit(field);
    }

    private void backToDeposit(MiningField field) {
        this.destination = new Position();
        var bfs = new BfsShortestPath(field.getMap());
        var path = bfs.getPath(this.position, this.destination);
        System.out.println("Path length to deposit: " + bfs.findShortestPathLength(this.position, this.destination));
        System.out.println("Path size to deposit " + path.size());
        for (int i = 0; i < path.size(); i++) System.out.println(path.get(i));
        for (var i = 1; i < path.size(); i++) {
            //System.out.println(this.position);
            //System.out.println(field.toString());
            field.freePosition(this.position);
            this.position = path.get(i);
            if (field.isGold(this.position)) {
                //this.send(GOLD_FOUND, field);
                System.out.println("Found gold and I'm busy!!");
            } else {
                field.setMinerInPosition(this.position);
            }
        }
    }

    public void dropGold(MiningField field) {
        this.free = true;
        send(GOLD_DROPPED, field);
        System.out.println("+++++++1");
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

    @Override
    public String toString() {
        return "---> Miner: " + this.id + " \n---> Position: " + position.toString();
    }

}