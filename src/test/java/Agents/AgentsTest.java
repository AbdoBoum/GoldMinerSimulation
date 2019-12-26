package Agents;

import Environment.MiningField;
import Utils.Position;
import org.junit.Test;

import static Agents.Agent.Type.ANNOUNCE_WINNER;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

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
        Miner miner = new Miner("124958", true, new Position(4, 4), 0, false, new Position());
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
        Miner miner = leader.getMinerByIndex(0);
        leader.updateScore(miner);
        assertEquals(1, miner.getScore());

        leader.broadcast(ANNOUNCE_WINNER, miner);
        for (int i = 0; i < 4; i++) {
            if (leader.getMinerByIndex(i).equals(miner)) assertThat(leader.getMinerByIndex(i).isWinner(), equalTo(true));
            else assertThat(leader.getMinerByIndex(i).isWinner(), equalTo(false));
        }

    }

    @Test
    public void testGoldHandling() {
        Leader leader = new Leader();
        Miner miner = leader.getMinerByIndex(0);
        miner.setPosition(new Position(4, 4));
        MiningField field = new MiningField();
        field.getMap()[4][4] = 'O';
        miner.pickGold(field);
        assertFalse(miner.isFree());
        assertThat(miner.getDirection(), equalTo(new Position(0, 0)));
        assertTrue(field.isFree(new Position(4, 4)));
        miner.dropGold(leader);
        assertTrue(miner.isFree());
        assertThat(miner.getScore(), equalTo(1));
    }

}
