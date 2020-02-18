package Agents;

public interface Agent {

    enum Type {
        ANNOUNCE_WINNER,
        GOLD_DROPPED,
        GOLD_FOUND,
        GOLD_POSITION
    }

    void broadcast(Type type, Object content);
    void send(Type type, Object... content);
}
