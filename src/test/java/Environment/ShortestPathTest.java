package Environment;

import Utils.Position;
import org.junit.Test;

import java.util.List;

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
        var bfs = new BfsShortestPath(map);
        var pathLength = bfs.findShortestPathLength(new Position(3, 3), new Position(0, 0));
        var actualPath = bfs.getPath(new Position(3, 3), new Position(0, 0));
        var expectedPath = List.of(
                new Position(3, 3), new Position(2, 4), new Position(1, 4),
                new Position(0, 3), new Position(0, 2), new Position(0, 1),
                new Position(0, 0)
        );
        assertEquals(pathLength, 6);
        assertSamePath(expectedPath, actualPath);
    }

    private void assertSamePath(List<Position> expectedPath, List<Position> actualPath) {
        for (var i = 0; i < expectedPath.size(); i++) {
            assertEquals(expectedPath.get(i), actualPath.get(i));
        }
    }


}
