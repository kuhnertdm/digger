import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Contains information for the Gold object, representing both the gold bag and
 * the released gold
 * 
 * @author kuhnerdm
 * 
 */
public class Gold extends Component {

	// Instance variables
	private static final int VALUE = 100;
	private final int WAIT_TIME = 75;
	private boolean isBagged;
	private boolean isFalling;
	private boolean isWaiting;
	private int cyclesUntilFalling;
	private int spacesMoved;

	/**
	 * Constructs a Gold object with no given values
	 * 
	 * @param level
	 */
	public Gold(Level level) {
		super();
		this.level = level;
		this.isBagged = true;
		this.isWaiting = false;
		this.cyclesUntilFalling = this.WAIT_TIME;
		this.spacesMoved = 0;
	}

	/**
	 * Returns the number of points picking up this itme gives to the player.
	 * 
	 * @return the value of gold
	 */
	public int getValue() {
		return VALUE;
	}

	@Override
	public void drawTo(Graphics2D g) {
		g.translate(this.getPosition().getX() * UI.CELL_SIDE_SIZE, this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
		if(this.isBagged) {
			g.setColor(Color.BLACK);
			g.fill(new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE));
			g.setColor(Color.YELLOW);
			g.fill(new Ellipse2D.Double(0, 20, UI.CELL_SIDE_SIZE, 30));
			g.translate(UI.CELL_SIDE_SIZE / 2, UI.CELL_SIDE_SIZE / 2);
			g.rotate(Math.PI / 4);
			g.fill(new Ellipse2D.Double(-5, -15, 5, 15));
			g.rotate(-Math.PI / 2);
			g.fill(new Ellipse2D.Double(-5, -15, 5, 15));
			g.rotate(Math.PI / 4);
			g.translate(-UI.CELL_SIDE_SIZE / 2, -UI.CELL_SIDE_SIZE / 2);
			g.setColor(Color.BLACK);
			g.drawString("$", 21, 38);
		}
		else {
			g.setColor(Color.BLACK);
			g.fill(new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE));
			g.setColor(Color.YELLOW);
			g.fill(new Ellipse2D.Double(0, 0, 20, 20));;
			g.fill(new Ellipse2D.Double(0, 25, 20, 20));;
			g.fill(new Ellipse2D.Double(25, 15, 20, 20));;
			g.setColor(Color.BLACK);
			g.drawString("$", 7, 14);
			g.drawString("$", 7, 39);
			g.drawString("$", 32, 29);
			
		}
		g.translate(-this.getPosition().getX() * UI.CELL_SIDE_SIZE, -this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
	}

	/**
	 * @return whether the gold is bagged (true) or spilled open (false)
	 */
	public boolean getIsBagged() {
		return this.isBagged;
	}



	@Override
	public void progressCycle() {
		if(this.isBagged){
		Coordinate pointBelow = new Coordinate(this.getPosition().getX(), this
				.getPosition().getY() + 1);
		if (pointBelow.getY() != 10 && this.level.getComponentAt(pointBelow) == null) {
			if (this.isWaiting == false) {
				this.isWaiting = true;
			} else {
				if (this.cyclesUntilFalling != 0) {
					this.cyclesUntilFalling--;
				} else {
					this.isFalling = true;
					this.moveTo(pointBelow);
					this.spacesMoved++;
					this.cyclesUntilFalling = this.WAIT_TIME;
				}
			}
		} else if (pointBelow.getY() != 10 && this.isFalling == true) {
			this.moveTo(pointBelow);
		} else if (pointBelow.getY() == 10 && this.isFalling == true) {
			this.collideWith(new Dirt(this.level));
		}
		}
	}

	/**
	 * Handles a collision with another component
	 * 
	 * @param component
	 *            the component to collide with
	 */
	@Override
	public void collideWith(Component component) {
		if (component instanceof Enemy && this.spacesMoved > 1) {
			component.die();
			this.getLevel().setCoordinateTo(component.getPosition(), this);
			this.getLevel().clearCoordinate(this.getPosition());
			this.setPosition(component.getPosition());
		} else if (component instanceof Player && this.spacesMoved > 1) {
			component.die();
		} else if ((component instanceof Dirt || component instanceof Emerald) && this.spacesMoved > 1) {
			this.isBagged = false;
		} else if ((component instanceof Dirt || component instanceof Emerald) && this.spacesMoved <= 1) {
			this.isBagged = true;
			this.isWaiting = false;
			this.cyclesUntilFalling = this.WAIT_TIME;
			this.spacesMoved = 0;
		} else if (component instanceof Gold && this.spacesMoved > 1) {
			this.isFalling = false;
			this.isBagged = true;
			this.isWaiting = false;
			this.cyclesUntilFalling = this.WAIT_TIME;
			this.spacesMoved = 0;
		}
	}
}
