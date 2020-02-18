package Simulator;

import Agents.Leader;
import Agents.Miner;
import Environment.MiningField;
import Environment.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static Simulator.Event.dropGold;
import static Simulator.Event.searchInPosition;
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
    public void testMock() {
        Mockito.when(owner.getMinerByIndex(0)).thenReturn(miner);
        Mockito.when(owner.getDiposites()).thenReturn(List.of(new Position(0, 0)));
        searchInPosition(miner, miningField, position);
        assertFalse(miner.isFree());
        dropGold(miner, miningField);
        assertTrue(miner.isFree());
    }


}
