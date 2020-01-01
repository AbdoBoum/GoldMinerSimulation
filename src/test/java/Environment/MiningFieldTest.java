package Environment;

import Agents.Leader;
import org.junit.Test;

import java.util.stream.IntStream;

import static Environment.MiningField.DEFAULT_GOLD_PIECES;
import static Environment.MiningField.DEFAULT_OBSTACLES;
import static Environment.MiningField.MAP_HEIGHT;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MiningFieldTest {

    @Test
    public void defaultEnvironmentCreation() {
        var miningField = new MiningField();
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
    public void countGoldPiecesAndObstacles() {
        var miningField = new MiningField();
        var goldPieces = 0L;
        var obstacles = 0L;
        var miners = 0L;
        for (var i = 0; i < MAP_HEIGHT; ++i) {
            var line = miningField.getMap()[i];
            goldPieces += IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .filter(x -> x == 'o')
                    .count();

            obstacles += IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .filter(x -> x == '#')
                    .count();

            miners += IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .filter(x -> x == 'M')
                    .count();
        }

        assertThat((int)goldPieces, equalTo(DEFAULT_GOLD_PIECES));
        assertThat((int)obstacles, equalTo(DEFAULT_OBSTACLES));
        assertThat((int)miners, equalTo(4));

    }

}
