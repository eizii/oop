import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class MazePanel extends JPanel {
  private Maze maze;
  private Player player;
  private List<Enemy> enemies;
  private List<PowerItem> powerItems;
  private Supplier<Integer> timeSupplier;
  private Supplier<Boolean> invincibleSupplier;
  private int stage;

  private final int CELL_SIZE = 30;

  public MazePanel(Maze maze, Player player, List<Enemy> enemies,List<Point> items, List<PowerItem> powerItems,Supplier<Integer> timeSupplier,Supplier<Boolean> invincibleSupplier,int stage) {
    this.maze = maze;
    this.player = player;
    this.enemies = enemies;
    this.powerItems = powerItems;
    this.timeSupplier = timeSupplier;
    this.invincibleSupplier = invincibleSupplier;
    this.stage = stage;
    this.setPreferredSize(new Dimension(
      maze.getGrid()[0].length * CELL_SIZE,
      maze.getGrid().length * CELL_SIZE + 40
    ));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int[][] grid = maze.getGrid();

    // 迷路描画
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[0].length; x++) {
        g.setColor(grid[y][x] == 1 ? Color.BLACK : Color.WHITE);
        g.fillRect(x * CELL_SIZE, y * CELL_SIZE + 40, CELL_SIZE, CELL_SIZE);
        g.setColor(Color.GRAY);
        g.drawRect(x * CELL_SIZE, y * CELL_SIZE + 40, CELL_SIZE, CELL_SIZE);
      }
    }

    // ゴール描画
    Point goal = maze.getGoal();
    g.setColor(Color.YELLOW);
    g.fillRect(goal.x * CELL_SIZE, goal.y * CELL_SIZE + 40, CELL_SIZE, CELL_SIZE);

    // 無敵アイテム描画
    g.setColor(Color.GRAY);
    for (PowerItem pi : powerItems) {
      Point pos = pi.getPosition();
      g.fillOval(pos.x * CELL_SIZE + 8, pos.y * CELL_SIZE + 40 + 8, CELL_SIZE - 16, CELL_SIZE - 16);
    }

    // プレイヤー描画
    Point p = player.getPosition();
    g.setColor(invincibleSupplier.get() ? new Color(255, 105, 180) : Color.RED); // 無敵時はピンク
    g.fillOval(p.x * CELL_SIZE + 5, p.y * CELL_SIZE + 40 + 5, 20, 20);

    // 敵描画
    g.setColor(Color.BLUE);
    for (Enemy e : enemies) {
      Point ep = e.getPosition();
      g.fillRect(ep.x * CELL_SIZE + 8, ep.y * CELL_SIZE + 40 + 8, 14, 14);
    }

    // タイマーとステージ番号
    g.setColor(Color.BLACK);
    g.setFont(new Font("SansSerif", Font.BOLD, 20));
    g.drawString("時間: " + timeSupplier.get() + " 秒", 10, 25);

    g.setColor(Color.BLACK);
    g.drawString("ステージ: " + stage, getWidth() - 150, 25);
  }
}
