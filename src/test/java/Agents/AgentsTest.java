package Agents;

import Utils.Position;
import org.junit.Test;

import static Agents.Agent.Type.ANNOUNCE_WINNER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class AgentsTest {

    @Test
    public void defaultMinerCreation() {
        Miner miner = new Miner();
        assertThat(miner.isFree(), equalTo(true));
        assertThat(miner.getPosition().getRow(), equalTo(0));
        assertThat(miner.getPosition().getCol(), equalTo(0));
        assertThat(miner.getScore(), equalTo(0));
        assertThat(miner.isWinner(), equalTo(false));
        Position p = miner.getPosition();
        miner.moveUp();
        assertThat(miner.getPosition().getRow(), lessThanOrEqualTo(p.getRow()));
    }

    @Test
    public void customMinerCreation() {
        Miner miner = new Miner("124958", true, new Position(4, 4), 0, false);
        assertThat(miner.isFree(), equalTo(true));
        assertThat(miner.getPosition().getRow(), equalTo(4));
        assertThat(miner.getPosition().getCol(), equalTo(4));
        assertThat(miner.getScore(), equalTo(0));
        assertThat(miner.getId(), equalTo("124958"));
    }

    @Test
    public void defaultLeaderCreation() {
        Leader leader = new Leader();
        assertThat(leader.getMiners().size(), equalTo(4));
        assertNotNull(leader.getMiners());
    }

    @Test
    public void testLeaderBehavior() {
        Leader leader = new Leader();
        Miner miner = (Miner)leader.getMiners().get(0);
        leader.updateScore(miner);
        assertEquals(1, miner.getScore());

        leader.broadcast(ANNOUNCE_WINNER, miner);
        for (Agent agent: leader.getMiners()) {
            if (agent.equals(miner)) assertThat(miner.isWinner(), equalTo(true));
            else assertThat(((Miner)agent).isWinner(), equalTo(false));
        }

    }

}
