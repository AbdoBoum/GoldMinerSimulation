package Environment;

import Agents.Leader;
import org.junit.Test;

import java.util.stream.IntStream;

import static Environment.MiningField.MAP_HEIGHT;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MiningFieldTest {

    @Test
    public void defaultEnvironmentCreation() {
        MiningField miningField = new MiningField();
        assertThat(miningField.getGoldPieces(), equalTo(30));
        assertThat(miningField.getObstacles(), equalTo(30));
        assertNotNull(miningField.getMap());
        assertNotNull(miningField.getOwner());
    }

    @Test
    public void customEnvironmentCreation() {
        MiningField miningField = new MiningField(50, 20, new Leader());
        assertThat(miningField.getGoldPieces(), equalTo(50));
        assertThat(miningField.getObstacles(), equalTo(20));
        assertThat(miningField.getMap()[0].length, equalTo(MAP_HEIGHT));
    }

    @Test
    public void countGoldPiecesAndObstacles() {
        MiningField miningField = new MiningField();
        long goldPieces = 0L;
        long obstacles = 0L;
        long miners = 0L;
        for (int i = 0; i < MAP_HEIGHT; ++i) {
            char[] line = miningField.getMap()[i];
            goldPieces += IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .filter(x -> x == 'O')
                    .count();

            obstacles += IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .filter(x -> x == 'X')
                    .count();

            miners += IntStream.range(0, line.length)
                    .mapToObj(c -> line[c])
                    .filter(x -> x == 'M')
                    .count();
        }

        assertThat((int)goldPieces, equalTo(30));
        assertThat((int)obstacles, equalTo(30));
        assertThat((int)miners, equalTo(4));

    }

}
