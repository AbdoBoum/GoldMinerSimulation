package Agents;

public interface Agent {

    enum Type {
        ANNOUNCE_WINNER,
        GOLD_DROPPED,
        BID,
        GOLD_POSITION
    }

    //TODO: add strategy pattern on those behaviors
    void broadcast(Type type, Object content);
    void send(Agent to, Type type);
}
