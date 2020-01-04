package Agents;

public interface Agent {

    enum Type {
        ANNOUNCE_WINNER,
        GOLD_DROPPED,
        GOLD_FOUND,
        GOLD_POSITION
    }

    //TODO: add strategy pattern on those behaviors
    void broadcast(Type type, Object content);
    void send(Type type, Object... content);
}
