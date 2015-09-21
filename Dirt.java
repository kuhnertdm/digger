import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Contains information for a Dirt component
 * 
 * @author kuhnerdm
 * 
 */
public class Dirt extends Component {

	//Instance variables
	
	private Ellipse2D.Double[] rocks;
	
	/**
	 * Constructs a new Dirt object with no given values
	 * 
	 * @param level
	 */
	public Dirt(Level level) {
		super();
		this.level = level;
		this.rocks = new Ellipse2D.Double[5];
		for (int i = 0; i < 5; i++) {
			rocks[i] = new Ellipse2D.Double(Math.random() * UI.CELL_SIDE_SIZE,
					Math.random() * UI.CELL_SIDE_SIZE, 1, 1);
		}
	}

	/**
	 * Draws the Dirt object to the given Graphics2D object
	 * 
	 * @param g
	 *            the object to draw to
	 */
	@Override
	public void drawTo(Graphics2D g) {
		g.translate(this.getPosition().getX() * UI.CELL_SIDE_SIZE, this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
		Rectangle2D.Double dirtRect = new Rectangle2D.Double(0, 0,
				UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE);
		g.setColor(new Color(102, 51, 0));
		g.fill(dirtRect);
		g.setColor(Color.BLACK);
		for(int i = 0; i < 5; i++) {
			g.draw(rocks[i]);
			g.fill(rocks[i]);
		}
		if (this.getPosition().getY() == 0) {
			Rectangle2D.Double grassRect = new Rectangle2D.Double(0, 0,
					UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE * 0.4);
			g.setColor(Color.GREEN);
			g.fill(grassRect);
			Rectangle2D.Double skyRect = new Rectangle2D.Double(0, 0,
					UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE * 0.2);
			g.setColor(new Color(51, 255, 255));
			g.fill(skyRect);
		}
		g.translate(-this.getPosition().getX() * UI.CELL_SIDE_SIZE, -this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
	}

	/**
	 * Used in other components, but not in the Dirt component
	 */
	@Override
	public void moveTo(Coordinate destination) {
		// Do nothing

	}

	/**
	 * Used in other components, but not in the Dirt component
	 */
	@Override
	public void progressCycle() {
		// Do nothing

	}

	/**
	 * Used in other components, but not in the Dirt component
	 */
	@Override
	public void collideWith(Component component) {
		// Do nothing

	}

}
