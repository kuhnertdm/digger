import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

/**
 * Contains information for an Emerald component
 * 
 * @author kuhnerdm
 * 
 */
public class Emerald extends Component {

	// Instance variables
	private static final int VALUE = 50;

	/**
	 * Constructs a new Emerald object with the given Level object
	 * 
	 * @param level
	 */
	public Emerald(Level level) {
		super();
		this.level = level;
	}

	/**
	 * Draws the Emerald object to the given Graphics2D object
	 * 
	 * @param g
	 *            the object to draw to
	 */
	@Override
	public void drawTo(Graphics2D g) {
		g.translate(this.getPosition().getX() * UI.CELL_SIDE_SIZE, this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
		
		Rectangle2D.Double blackBG = new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE);
		g.setColor(new Color(102, 51, 0));
		g.fill(blackBG);
		
		int[] xCoords = new int[6];
		int[] yCoords = new int[6];
		
		xCoords[0] = 0;
		xCoords[1] = 8;
		xCoords[2] = 25;
		xCoords[3] = 41;
		xCoords[4] = 49;
		xCoords[5] = 25;
		
		yCoords[0] = 15;
		yCoords[1] = 6;
		yCoords[2] = 1;
		yCoords[3] = 6;
		yCoords[4] = 15;
		yCoords[5] = 49;
		
		Polygon emeraldBG = new Polygon(xCoords, yCoords, 6);
		g.setColor(Color.GREEN);
		g.fill(emeraldBG);
		
		g.setColor(Color.BLACK);
		g.drawLine(0, 15, 25, 49);
		g.drawLine(8, 6, 25, 49);
		g.drawLine(25, 1, 25, 49);
		g.drawLine(41, 6, 25, 49);
		g.drawLine(49, 15, 25, 49);
		g.drawLine(0, 15, 49, 15);
		
		g.translate(-this.getPosition().getX() * UI.CELL_SIDE_SIZE, -this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
	}

	/**
	 * Returns the number of points picking up this item gives to the player.
	 * 
	 * @return the value of emerald
	 */
	public int getValue() {
		return VALUE;
	}



	/**
	 * Used in other components, but not the Emerald component
	 */
	@Override
	public void collideWith(Component component) {
		// Do nothing

	}

	/**
	 * Used in other components, but not the Emerald component
	 */
	@Override
	public void progressCycle() {
		// Do nothing

	}

}
