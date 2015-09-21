import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Contains information for the Bullet object
 * 
 * @author kuhnerdm
 * 
 */
public class Bullet extends Component {

	// Instance variables
	private String direction;
	private static final int CYCLES_PER_TURN = 15;
	private int cyclesUntilTurn;

	/**
	 * Constructs a new Bullet object given a starting direction and a level
	 * 
	 * @param direction
	 *            the starting direction of the Bullet
	 * @param level
	 *            the level in which to put the Bullet
	 */
	public Bullet(String direction, Level level) {
		super();
		this.level = level;
		this.direction = direction;
		this.cyclesUntilTurn = CYCLES_PER_TURN;
	}

	/**
	 * Draws the Bullet object to the given Graphics2D object
	 * 
	 * @param g
	 *            the Graphics2D object to draw to
	 */
	@Override
	public void drawTo(Graphics2D g) {
		g.translate(this.getPosition().getX() * UI.CELL_SIDE_SIZE, this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
		Rectangle2D.Double bgRect = new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE);
		g.setColor(Color.BLACK);
		g.fill(bgRect);
		Rectangle2D.Double bulletRect = new Rectangle2D.Double(20, 20, 10, 10);
		g.setColor(Color.GRAY);
		g.fill(bulletRect);
		g.translate(-this.getPosition().getX() * UI.CELL_SIDE_SIZE, -this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
	}

	/**
	 * Moves the Bullet one space in the direction of travel
	 */
	public void takeTurn() {
		switch (this.direction) {
		case "up":
			if (this.getPosition().getY() > 0)
				this.moveTo(new Coordinate(this.getPosition().getX(), this
						.getPosition().getY() - 1));
			else
				this.die();
			break;
		case "down":
			if (this.getPosition().getY() < 9)
				this.moveTo(new Coordinate(this.getPosition().getX(), this
						.getPosition().getY() + 1));
			else
				this.die();
			break;
		case "left":
			if (this.getPosition().getX() > 0)
				this.moveTo(new Coordinate(this.getPosition().getX() - 1, this
						.getPosition().getY()));
			else
				this.die();
			break;
		case "right":
			if (this.getPosition().getX() < 9)
				this.moveTo(new Coordinate(this.getPosition().getX() + 1, this
						.getPosition().getY()));
			else
				this.die();
			break;
		}
	}

	/**
	 * Gets the position of the sprite on the drawing component
	 * 
	 * @return the sprite position
	 */


	/**
	 * Reacts to a cycle change in the game
	 */
	@Override
	public void progressCycle() {
		if (this.cyclesUntilTurn == 0) {
			this.takeTurn();
			this.cyclesUntilTurn = CYCLES_PER_TURN;
		} else {
			this.cyclesUntilTurn--;
		}
	}

	/**
	 * Collides with another component
	 * 
	 * @param component
	 *            the component to collide with
	 */
	@Override
	public void collideWith(Component component) {
		this.die();
		if (component instanceof Enemy) {
			component.die();
		}
	}

}
