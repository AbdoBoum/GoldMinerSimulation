package Environment;

import Utils.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShortestPathTest {

    @Test
    public void testGettingShortestPath() {
        char[][] map = {
                {'D', '.', '.', '.', '.'},
                {'.', '#', '#', '#', '.'},
                {'.', '#', '#', '.', '.'},
                {'#', '#', '#', 'M', '#'}
        };
        BfsShortestPath bfs = new BfsShortestPath(map);
        int shortestPathDistance = bfs.findShortestPath(new Position(3, 3));
        assertEquals(shortestPathDistance, 6);
    }


}
