import java.awt.Point;

public class Player {
  private Point pos;

  public Player(Point start) {
    pos = new Point(start);
  }

  public void move(String dir, int[][] grid) {
    int dy = 0, dx = 0; 
    switch (dir) {
      case "UP": dy = -1; break;
      case "DOWN": dy = 1; break;
      case "LEFT": dx = -1; break;
      case "RIGHT": dx = 1; break;
    }

    int ny = pos.y + dy; // 行
    int nx = pos.x + dx; // 列

    if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length && grid[ny][nx] == 0) {
      pos.setLocation(nx, ny);
    }
  }

  public Point getPosition() {
    return pos;
  }

  public void reset(Point start) {
    pos.setLocation(start);
  }
}
