import javax.swing.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer; 
import java.util.Iterator;


public class GameManager {
  private JFrame frame;
  private Maze maze;
  private Player player;
  private List<Enemy> enemies;
  private List<Point> items;
  private List<PowerItem> powerItems;
  private MazePanel panel;
  private int elapsedTime = 0;
  private int stage = 1;
  private Timer gameTimer;
  private Timer invincibleTimer;
  private boolean isInvincible = false;

  public void start() {
    frame = new JFrame("迷路ゲーム");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    showStartPanel();
  }

  private void showStartPanel() {
    StartPanel startPanel = new StartPanel(frame, () -> {
      stage = 1;
      startGame(stage);
    });
    frame.setContentPane(startPanel);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private void startGame(int stage) {
    for (var l : frame.getKeyListeners()) {
      frame.removeKeyListener(l);
    }

    int width = 21;
    int height = 21;
    int enemyCount = (stage - 1);

    if (stage > 10) {
      JOptionPane.showMessageDialog(frame, "すべてのステージをクリアしました！ゲームを終了します。");
      System.exit(0);
    }

    maze = new Maze(height, width);
    player = new Player(maze.getStart());

    enemies = new ArrayList<>();
    for (int i = 0; i < enemyCount; i++) {
      enemies.add(new Enemy(findFree()));
    }

    items = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      items.add(findFree());
    }

    powerItems = new ArrayList<>();
    powerItems.add(new PowerItem(findFree()));

    elapsedTime = 0;

    panel = new MazePanel(maze, player, enemies, items, powerItems,
                          () -> elapsedTime, () -> isInvincible, stage);
    frame.setContentPane(panel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.requestFocusInWindow();

    if (gameTimer != null) gameTimer.stop();
    gameTimer = new Timer(1000, e -> {
      elapsedTime++;
      for (Enemy enemy : enemies) {
        enemy.move(maze.getGrid());
      }

      for (Enemy enemy : enemies) {
        if (!isInvincible && enemy.getPosition().equals(player.getPosition())) {
          gameTimer.stop();
          JOptionPane.showMessageDialog(frame, "ゲームオーバー！");
          showStartPanel();
          return;
        }
      }

      panel.repaint();
    });
    gameTimer.start();

    frame.addKeyListener(new java.awt.event.KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) player.move("UP", maze.getGrid());
        else if (code == KeyEvent.VK_DOWN) player.move("DOWN", maze.getGrid());
        else if (code == KeyEvent.VK_LEFT) player.move("LEFT", maze.getGrid());
        else if (code == KeyEvent.VK_RIGHT) player.move("RIGHT", maze.getGrid());

        // 無敵アイテム取得チェック
        Iterator<PowerItem> pit = powerItems.iterator();
        while (pit.hasNext()) {
          PowerItem power = pit.next();
          if (player.getPosition().equals(power.getPosition())) {
            isInvincible = true;
            pit.remove();
            if (invincibleTimer != null) invincibleTimer.stop();
            invincibleTimer = new Timer(10000, ev -> {
              isInvincible = false;
              panel.repaint();
            });
            invincibleTimer.setRepeats(false);
            invincibleTimer.start();
          }
        }

        // 敵との接触チェック
        for (Enemy enemy : enemies) {
          if (!isInvincible && enemy.getPosition().equals(player.getPosition())) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(frame, "ゲームオーバー！");
            showStartPanel();
            return;
          }
        }

        // ゴール到達チェック
        if (player.getPosition().equals(maze.getGoal())) {
          gameTimer.stop();
          int result = JOptionPane.showConfirmDialog(frame,
              "クリア！時間: " + elapsedTime + " 秒\n次のステージに進みますか？",
              "ステージクリア",
              JOptionPane.YES_NO_OPTION);
          if (result == JOptionPane.YES_OPTION) {
            startGame(stage + 1);
          } else {
            showStartPanel();
          }
        }

        panel.repaint();
      }
    });
  }

  private Point findFree() {
    Random rand = new Random();
    int[][] grid = maze.getGrid();
    while (true) {
      int x = rand.nextInt(grid[0].length);
      int y = rand.nextInt(grid.length);
      Point p = new Point(x, y);
      if (grid[y][x] == 0 && !p.equals(maze.getStart()) && !p.equals(maze.getGoal())) {
        return p;
      }
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GameManager().start());
  }
}
