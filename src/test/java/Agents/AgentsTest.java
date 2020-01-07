package Agents;

import Environment.MiningField;
import Utils.Position;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

public class AgentsTest {

    @Test
    public void defaultMinerCreation() {
        var miner = new Miner();
        assertThat(miner.isFree(), equalTo(true));
        assertThat(miner.getPosition().getRow(), equalTo(0));
        assertThat(miner.getPosition().getCol(), equalTo(0));
        assertThat(miner.getScore(), equalTo(0));
        assertThat(miner.isWinner(), equalTo(false));
    }

    @Test
    public void customMinerCreation() {
        var miner = new Miner("124958", true, new Position(4, 4), 0, false, new Position());
        assertThat(miner.isFree(), equalTo(true));
        assertThat(miner.getPosition().getRow(), equalTo(4));
        assertThat(miner.getPosition().getCol(), equalTo(4));
        assertThat(miner.getScore(), equalTo(0));
        assertThat(miner.getId(), equalTo("124958"));
    }

    @Test
    public void defaultLeaderCreation() {
        var leader = new Leader();
        assertThat(leader.getMiners().size(), equalTo(4));
        assertNotNull(leader.getMiners());
    }

    @Test
    public void testLeaderBehavior() {
        var leader = new Leader();
        var miner = leader.getMinerByIndex(0);
        leader.updateScore(miner);
        assertEquals(leader.getTeamMaxScore(), 1);
        assertEquals(1, miner.getScore());
        for (var i = 0; i < 4; i++) {
            if (leader.getMinerByIndex(i).equals(miner)) assertThat(leader.getMinerByIndex(i).isWinner(), equalTo(true));
            else assertThat(leader.getMinerByIndex(i).isWinner(), equalTo(false));
        }
    }

    @Test
    public void goldSearchingAndHandling() {
        var field = new MiningField();
        var miner = field.getOwner().getMinerByIndex(0);
        miner.setPosition(new Position(4, 4));
        field.getMap()[0][1] = 'o';
        System.out.println(field);
        miner.searchInPosition(field, new Position(0, 1));
        assertFalse(miner.isFree());
        assertThat(miner.getDestination(), equalTo(new Position(0, 0)));
        miner.dropGold(field);
        assertTrue(miner.isFree());
        assertThat(miner.getScore(), equalTo(1));
        assertThat(miner.getPosition(), equalTo(new Position()));
    }

}
