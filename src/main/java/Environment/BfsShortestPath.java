package Environment;

import java.util.*;

/**
 * this class calculate the shortest paths in a mining field map based on BFS algorithm
 */
public class BfsShortestPath {
    /**
     * the mining field map
     */
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
    private Position[][] parent;

    private List<Position> path;

    /**
     * constructor who take a map in parameter
     * @param map the map of the mining field
     */
    public BfsShortestPath(char[][] map) {
        this.map = map;
    }

    /**
     * Function used to get the shortest path from position to an other position
     * @param start the start position
     * @param end the end position
     * @return return collection of positions to reach the end position
     */
    public List<Position> getPath(Position start, Position end) {
        findShortestPathLength(start, end);
        var p = end;
        while ((p != null) && !p.equals(start)) {
            path.add(p);
            p = parent[p.getRow()][p.getCol()];
        }
        if (p != null) path.add(start);
        Collections.reverse(path);
        return path;
    }

    /**
     * Function used to get the shortest path from position to an other position in a specific map
     * @param start the start position
     * @param end the end position
     * @param map the map to search the shortest path
     * @return return collection of position to reach the end position
     */
    public static List<Position> getPath(Position start, Position end, char[][] map){
        var bfs = new BfsShortestPath(map);
        return bfs.getPath(start,end);
    }

    /**
     * the function calculate the length a shortest path from source position to a destination position in the map
     * @param start the start position
     * @param end the end position
     * @return return the length of the path
     */
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
                reached = true;
                break;
            }
            exploreNeighbours(row, col);
            nodesLeftInLayer--;
            if (nodesLeftInLayer == 0) {
                nodesLeftInLayer = nodesInNextLayer;
                nodesInNextLayer = 0;
                moveCount++;
            }
        }
        return reached ? moveCount : -1;
    }

    /**
     * Function init initialise the members of the class
     */

    private void init() {
        rQueue = new LinkedList<>();
        cQueue = new LinkedList<>();
        moveCount = 0;
        nodesLeftInLayer = 1;
        nodesInNextLayer = 0;
        visited = new boolean[map.length][map[0].length];
        parent = new Position[map.length][map[0].length];
        path = new ArrayList<>();
    }

    /**
     * the function verifies if the coordinates match a position
     * @param row the index of row
     * @param col the index of column
     * @param end the position
     * @return boolean to describe the position
     */
    private boolean atEnd(int row, int col, Position end) {
        return row == end.getRow() && col == end.getCol();
    }

    /**
     * this function allow to explore neighbours of a position
     * @param row index of row position
     * @param col index of column position
     */
    private void exploreNeighbours(int row, int col) {
        for (var i = 0; i < 8; i++) {
            var rr = row + dr[i];
            var cc = col + dc[i];
            if (isValidMove(rr, cc)) {
                parent[rr][cc] = new Position(row, col);
                rQueue.add(rr);
                cQueue.add(cc);
                visited[rr][cc] = true;
                nodesInNextLayer++;
            }
        }
    }

    /**
     * To verifies if the position is free
     * @param row row position in the map
     * @param col column position in the map
     * @return boolean who describe the position
     */
    private boolean isValidMove(int row, int col) {
        return (isInTheGrid(row, col) && !visited[row][col] && !isObstacle(row, col));
    }

    /**
     * function who describe if a position exist in the map
     * @param row the row position
     * @param col the column position
     * @return boolean who describe the position
     */
    private boolean isInTheGrid(int row, int col) {
        return row >= 0 && col >= 0 && row < map.length && col < map[0].length;
    }

    /**
     * To verifies if the position is an obstacle
     * @param row row position in the map
     * @param col column position in the map
     * @return boolean who describe the position
     */
    private boolean isObstacle(int row, int col) {
        return (map[row][col] == '#') || (map[row][col] == 'M');
    }

}
