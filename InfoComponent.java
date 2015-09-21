import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * JComponent that displays statistics for a player (score, lives)
 *
 * @author grobleke. Created Nov 10, 2014.
 */
public class InfoComponent extends JComponent {

	private Player player;

	/**
	 * Constructs a new infoComponent for the specified player.
	 *
	 * @param p
	 */
	public InfoComponent(Player p) {
		super();
		this.player = p;
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 60);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(50, 40);
		g2.drawString("Score: " + this.player.getScore(), 0, 0);
		g2.translate(100, 0);
		g2.drawString("Lives: " + this.player.getLives(), 0, 0);
		g2.translate(-150, -40);
	}
}
