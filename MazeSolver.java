import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MazeSolver {
  private List<Point> path;

  public MazeSolver() {
    path = new ArrayList<>();
  }

  public void solve(int[][] grid, Point start, Point goal) {
    path.clear();
    boolean[][] visited = new boolean[grid.length][grid[0].length];
    HashMap<Point, Point> parentMap = new HashMap<>();
    LinkedList<Point> queue = new LinkedList<>();
    queue.add(start);
    visited[start.y][start.x] = true;

    while (!queue.isEmpty()) {
      Point current = queue.poll();
      if (current.equals(goal)) break;

      int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
      for (int[] dir : directions) {
        int cx = current.x + dir[0];
        int cy = current.y + dir[1];
        if (cy >= 0 && cy < grid.length && cx >= 0 && cx < grid[0].length
          && grid[cy][cx] == 0 && !visited[cy][cx]) {
          Point next = new Point(cx, cy);
          queue.add(next);
          visited[cy][cx] = true;
          parentMap.put(next, current);
        }
      }
    }

    for (Point p = goal; p != null && parentMap.containsKey(p); p = parentMap.get(p)) {
      path.add(0, p);
    }
    if (start.equals(goal)) path.add(start);
  }

  public List<Point> getPath() {
    return path;
  }
}
