import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
  public StartPanel(JFrame frame, Runnable startGameAction) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // 余白

    JLabel title = new JLabel("迷路ゲーム", SwingConstants.CENTER);
    title.setFont(new Font("SansSerif", Font.BOLD, 36));
    title.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel description = new JLabel(
      "<html><div style='text-align: center;'>"
      + "<b>【操作方法】</b><br>"
      + "↑：上に移動　↓：下に移動<br>"
      + "←：左に移動　→：右に移動<br><br>"
      + "<b>【目的】</b><br>"
      + "敵を避けてゴールまでたどり着こう！<br>"
      + "ステージが進むごとに迷路が広くなり敵も増える！<br><br>"
      + "<b>【アイテム】</b><br>"
      + "🟧 無敵アイテム：取ると10秒間敵をすり抜けられる！<br>"
      + "　　取るとプレイヤーの色がピンクになります。"
      + "</div></html>"
    );
    description.setFont(new Font("SansSerif", Font.PLAIN, 16));
    description.setAlignmentX(Component.CENTER_ALIGNMENT);
    description.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

    JButton startButton = new JButton("スタート");
    startButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startButton.addActionListener(e -> startGameAction.run());

    add(Box.createVerticalGlue());
    add(title);
    add(Box.createVerticalStrut(30));
    add(description);
    add(Box.createVerticalStrut(30));
    add(startButton);
    add(Box.createVerticalGlue());
  }
}
