package Environment;

import Utils.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

public class BfsShortestPath {

    private char[][] map;
    private Queue<Integer> rQueue;
    private Queue<Integer> cQueue;

    // to track the number of steps taken
    private int moveCount;
    private int nodesLeftInLayer;
    private int nodesInNextLayer;

    // to process neighbours
    private boolean[][] visited;
    private int[] dr = {0, 1, 0, -1, 1, -1, 1, -1};
    private int[] dc = {1, 0, -1, 0, 1, -1, -1, 1};

    private List<Position> path;

    public BfsShortestPath(char[][] map) {
        this.map = map;
    }

    public List<Position> getPath(Position start, Position end) {
        findShortestPathLength(start, end);
        return path;
    }

    public int findShortestPathLength(Position start, Position end) {
        init();
        var reached = false;
        var row = start.getRow();
        var col = start.getCol();
        rQueue.add(row);
        cQueue.add(col);
        visited[row][col] = true;
        while (!rQueue.isEmpty()) {
            row = rQueue.poll();
            col = cQueue.poll();
            if (atEnd(row, col, end)) {
                path.add(new Position(row, col));
                reached = true;
                break;
            }
            exploreNeighbours(row, col);
            nodesLeftInLayer--;
            if (nodesLeftInLayer == 0) {
                path.add(new Position(row, col));
                nodesLeftInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                moveCount++;
            }
        }
        return reached ? moveCount : -1;
    }

    private void init() {
        rQueue = new LinkedList<>();
        cQueue = new LinkedList<>();
        moveCount = 0;
        nodesLeftInLayer = 1;
        nodesInNextLayer = 0;
        visited = new boolean[map.length][map[0].length];
        path = new ArrayList<>();
    }

    private boolean atEnd(int row, int col, Position end) {
        return row == end.getRow() && col == end.getCol();
    }

    private void exploreNeighbours(int row, int col) {
        for (var i = 0; i < 8; i++) {
            var rr = row + dr[i];
            var cc = col + dc[i];
            if (isValidMove(rr, cc)) {
                rQueue.add(rr);
                cQueue.add(cc);
                visited[rr][cc] = true;
                nodesInNextLayer++;
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return isInTheGrid(row, col) && !visited[row][col] && !isObstacle(row, col);
    }

    private boolean isInTheGrid(int row, int col) {
        return row >= 0 && col >= 0 && row < map.length && col < map[0].length;
    }

    private boolean isObstacle(int row, int col) {
        return map[row][col] == '#';
    }
}
