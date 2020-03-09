package agents;

/**
 *  the interface contain signature methods used by the agents in the system
 * @author Boumahdi
 */
public interface Agent {
    /**
     * Enumeration represent the type the broadcast or the send information
     */
    enum Type {
        ANNOUNCE_WINNER,
        GOLD_DROPPED,
        GOLD_FOUND,
        GOLD_POSITION
    }

    /**
     * the method used to broadcast an information to all agents.
     * @param type the type of information to broadcast
     * @param content the reference of the sender
     */
    void broadcast(Type type, Object content);

    /**
     * this method is used to send a specific information
     * @param type the type of information to send
     * @param content the information
     */
    void send(Type type, Object... content);
}
