package Agents;

import Utils.Position;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class AgentsTest {

    @Test
    public void defaultMinerCreation() {
        Miner miner = new Miner();
        assertThat(miner.isFree(), equalTo(true));
        assertThat(miner.getPosition().getRow(), equalTo(0));
        assertThat(miner.getPosition().getCol(), equalTo(0));
        Position p = miner.getPosition();
        miner.moveUp();
        assertThat(miner.getPosition().getRow(), lessThanOrEqualTo(p.getRow()));
    }

    @Test
    public void CustomMinerCreation() {
        Miner miner = new Miner("124958", true, new Position(4, 4));
        assertThat(miner.isFree(), equalTo(true));
        assertThat(miner.getPosition().getRow(), equalTo(4));
        assertThat(miner.getPosition().getCol(), equalTo(4));
        assertThat(miner.getId(), equalTo("124958"));
    }

}
