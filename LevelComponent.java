import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * JComponent that holds the graphics of a level.
 *
 * @author grobleke. Created Oct 30, 2014.
 */
public class LevelComponent extends JComponent {
	private Level level;

	/**
	 * Constructs a new LevelComponent for the specified level.
	 *
	 * @param level
	 */
	public LevelComponent(Level level) {
		super();
		this.level = level;
	}

	
	/**
	 * Change the level this LevelComponent displays
	 *
	 * @param level
	 */
	public void setLevel(Level level){
		this.level = level;
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(500, 500);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (this.level.getGrid() != null) {
			for (int i = 0; i < this.level.getGrid().length; i++) {
				for (int j = 0; j < this.level.getGrid()[0].length; j++) {
					Component c = this.level.getGrid()[i][j];
					if (c == null) { //empty space; draw a black square
						g2.translate(i * UI.CELL_SIDE_SIZE, j
								* UI.CELL_SIDE_SIZE);
						Rectangle2D.Double emptyRect = new Rectangle2D.Double(
								0, 0, UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE);
						g2.setColor(Color.BLACK);
						g2.fill(emptyRect);
						if(j == 0) {
							Rectangle2D.Double skyRect = new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE * 0.4);
							g2.setColor(new Color(51, 255, 255));
							g2.fill(skyRect);
						}
						g2.translate(-i * UI.CELL_SIDE_SIZE, -j
								* UI.CELL_SIDE_SIZE);
					} else { //draw the component
						c.drawTo(g2);
					}
				}
			}
		}
	}

}
