import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Maze {
  private int[][] grid;
  private Point start;
  private Point goal;

  public Maze(int rows, int cols) {
    generateMaze(rows, cols);
  }

  private void generateMaze(int rows, int cols) {
    grid = new int[rows][cols];
    for (int i = 0; i < rows; i++) Arrays.fill(grid[i], 1);
    start = new Point(1, 1);
    goal = new Point(cols - 2, rows - 2);
    dfs(start.y, start.x);
    grid[start.y][start.x] = 0;
    grid[goal.y][goal.x] = 0;
    Random rand = new Random();
    for (int i = 0; i < 10; i++) {
      int x = rand.nextInt(cols - 2) + 1;
      int y = rand.nextInt(rows - 2) + 1;
      if (grid[y][x] == 1 && adjacentPaths(y, x) >= 2) grid[y][x] = 0;
    }
  }

  private void dfs(int y, int x) {
    int[][] dirs = {{2,0},{-2,0},{0,2},{0,-2}};
    Collections.shuffle(Arrays.asList(dirs));
    grid[y][x] = 0;
    for (int[] d : dirs) {
      int ny = y + d[0], nx = x + d[1];
      if (ny > 0 && ny < grid.length - 1 && nx > 0 && nx < grid[0].length - 1 && grid[ny][nx] == 1) {
        grid[y + d[0]/2][x + d[1]/2] = 0;
        dfs(ny, nx);
      }
    }
  }

  private int adjacentPaths(int y, int x) {
    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    int count = 0;
    for (int[] d : dirs) {
      int ny = y + d[0], nx = x + d[1];
      if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length && grid[ny][nx] == 0) count++;
    }
    return count;
  }

  public int[][] getGrid() { 
    return grid;
  }
  public Point getStart() {
     return start; 
  }
  public Point getGoal() {
     return goal; 
  }
}
