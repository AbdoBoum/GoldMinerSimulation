package simulator;

import agents.Leader;
import agents.Miner;
import environment.MiningField;
import environment.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static simulator.Event.dropGold;
import static simulator.Event.searchInPosition;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EventsTest {

    private char[][] map = {
            {'D', '*', '*', '*', '*'},
            {'*', '#', 'o', '#', '*'},
            {'*', '#', '#', '*', '*'},
            {'#', '#', '#', 'M', '#'}
    };

    @Mock
    private Leader owner;

    @InjectMocks
    private MiningField miningField;

    private Position position;
    private Position minerPosition;

    Miner miner;

    @Before
    public void init() {
        miningField.setMap(map);
        position = new Position(1, 2);
        minerPosition = new Position(3, 3);
        miner = new Miner("07e6f3b4", true, minerPosition, 0, false, position);
    }

    @Test
    public void searchGoldTest() {
        Mockito.when(owner.getMinerByIndex(0)).thenReturn(miner);
        Mockito.when(owner.getDiposites()).thenReturn(List.of(new Position(0, 0)));
        searchInPosition(miner, miningField, position);
        assertFalse(miner.isFree());
        dropGold(miner, miningField);
        assertTrue(miner.isFree());
    }


}
