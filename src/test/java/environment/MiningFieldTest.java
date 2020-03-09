package environment;

import agents.Leader;
import org.junit.Before;
import org.junit.Test;

import static environment.MiningField.DEFAULT_GOLD_PIECES;
import static environment.MiningField.DEFAULT_OBSTACLES;
import static environment.MiningField.MAP_HEIGHT;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class MiningFieldTest {

    MiningField miningField;

    @Before
    public void init() {
        miningField = new MiningField();
    }

    @Test
    public void defaultEnvironmentCreation() {
        assertThat(miningField.getGoldPieces(), equalTo(DEFAULT_GOLD_PIECES));
        assertThat(miningField.getObstacles(), equalTo(DEFAULT_OBSTACLES));
        assertNotNull(miningField.getMap());
        assertNotNull(miningField.getOwner());
    }

    @Test
    public void customEnvironmentCreation() {
        var miningField = new MiningField(50, 20, new Leader());
        assertThat(miningField.getGoldPieces(), equalTo(50));
        assertThat(miningField.getObstacles(), equalTo(20));
        assertThat(miningField.getMap()[0].length, equalTo(MAP_HEIGHT));
    }

    @Test
    public void environmentBehavior() {
        var position = new Position(1, 0);
        miningField.freePosition(position);
        assertTrue(miningField.isFreePosition(position));
        assertFalse(miningField.isGold(position));
        miningField.setMinerInPosition(position);
        assertFalse(miningField.isFreePosition(position));
        assertThat(miningField.getMap()[1][0], equalTo('M'));
    }

}
