import java.awt.Point;
import java.util.Random;

public class Enemy {
  private Point pos;
  private Random rand = new Random();

  public Enemy(Point start) {
    pos = new Point(start);
  }

  public Point getPosition() {
    return pos;
  }

  public void move(int[][] grid) {
    int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    int[] dir = directions[rand.nextInt(4)];
    int nx = pos.x + dir[0];
    int ny = pos.y + dir[1];

    if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length && grid[ny][nx] == 0) {
      pos.setLocation(nx, ny);
    }
  }

  public void reset(Point start) {
    pos.setLocation(start);
  }
}
