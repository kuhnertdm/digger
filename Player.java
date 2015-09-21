import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;

/**
 * Contains information for the Player object
 * 
 * @author kuhnerdm
 * 
 */
public class Player extends Component {
	private static final int CYCLES_PER_TURN = 30;
	private static final int WEAPON_RECHARGE_CYCLES = 75;
	private static final int DEFAULT_LIVES = 3;
	private String direction;
	private int weaponCooldown;
	private boolean alive;
	private int score;
	private int lives;
	private int cyclesUntilTurn;

	/**
	 * Constructs a new Player object with no given values
	 */
	public Player() {
		super();
		this.cyclesUntilTurn = 0;
		this.weaponCooldown = 0;
		this.alive = true;
		this.score = 0;
		this.lives = DEFAULT_LIVES;
	}

	/**
	 * Constructs a new Player as part of the specified level.
	 *
	 * @param l
	 */
	public Player(Level l) {
		super();
		this.cyclesUntilTurn = 0;
		this.level = l;
		this.weaponCooldown = 0;
		this.alive = true;
		this.score = 0;
		this.lives = DEFAULT_LIVES;
	}

	
	/**
	 * Changes the level the hero belongs to to the specified level.
	 *
	 * @param l
	 */
	public void setLevel(Level l) {
		this.level = l;
	}

	/**
	 * Determines whether this player is ready to take a turn or not.
	 * 
	 * @return the player's readiness
	 */
	public Boolean ready() {
		if (this.cyclesUntilTurn == 0) {
			return true;
		} else {
			return false;
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
		if (component instanceof Enemy) {
			this.die();
		} else {
			if (component instanceof Emerald) {
				this.score += ((Emerald) component).getValue();
				component.die();
			} else if (component instanceof Gold
					&& ((Gold) component).getIsBagged() == false) {
				this.score += ((Gold) component).getValue();
				component.die();
			} else if (component instanceof Gold
					&& ((Gold) component).getIsBagged() == true) {
				if (this.getPosition().getY() == component.getPosition().getY()) {
					if (this.getPosition().getX() < component.getPosition()
							.getX()) {
						Coordinate pointToRight = new Coordinate(component
								.getPosition().getX() + 1, component
								.getPosition().getY());
						if (this.level.getComponentAt(pointToRight) == null) {
							Coordinate destination = component.getPosition();
							component.moveTo(pointToRight);
							this.getLevel().setCoordinateTo(destination, this);
							this.getLevel().clearCoordinate(this.getPosition());
							this.setPosition(destination);
						}
					}
					else {
						Coordinate pointToLeft = new Coordinate(component
								.getPosition().getX() - 1, component
								.getPosition().getY());
						if (this.level.getComponentAt(pointToLeft) == null) {
							Coordinate destination = component.getPosition();
							component.moveTo(pointToLeft);
							this.getLevel().setCoordinateTo(destination, this);
							this.getLevel().clearCoordinate(this.getPosition());
							this.setPosition(destination);
						}
					}
				}
				return;
			}
			Coordinate destination = component.getPosition();
			component.die();
			this.getLevel().setCoordinateTo(destination, this);
			this.getLevel().clearCoordinate(this.getPosition());
			this.setPosition(destination);
		}
	}

	/**
	 * 
	 * @return the player's current score
	 */
	public int getScore() {
		return this.score;
	}

	@Override
	public void drawTo(Graphics2D g) {
		g.translate(this.getPosition().getX() * UI.CELL_SIDE_SIZE, this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
		g.setColor(Color.BLACK);
		g.fill(new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE, UI.CELL_SIDE_SIZE));
		if(this.direction == null) {
			g.setColor(Color.ORANGE);
			g.fill(new Ellipse2D.Double(0, 15, 49, 19));
			g.setColor(Color.WHITE);
			g.fill(new Ellipse2D.Double(7, 34, 10, 8));
			g.fill(new Ellipse2D.Double(32, 34, 10, 8));
			g.setColor(Color.BLACK);
			g.drawString("D", 30, 29);
		}
		else {
			switch(this.direction) {
			
			case "up" :
				g.setColor(Color.ORANGE);
				g.fill(new Ellipse2D.Double(15, 0, 20, 49));
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(13, 4, 4, 10));
				g.fill(new Ellipse2D.Double(13, 40, 4, 10));
				g.fill(new Ellipse2D.Double(33, 4, 4, 10));
				g.fill(new Ellipse2D.Double(33, 40, 4, 10));
				g.setColor(Color.BLACK);
				g.drawString("D", 21, 20);
				break;
				
			case "down" :
				g.setColor(Color.ORANGE);
				g.fill(new Ellipse2D.Double(15, 0, 20, 49));
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(13, 4, 4, 10));
				g.fill(new Ellipse2D.Double(13, 40, 4, 10));
				g.fill(new Ellipse2D.Double(33, 4, 4, 10));
				g.fill(new Ellipse2D.Double(33, 40, 4, 10));
				g.setColor(Color.BLACK);
				g.rotate(Math.PI);
				g.drawString("D", -29, -26);
				g.rotate(-Math.PI);
				break;
			
			case "left" :
				g.setColor(Color.ORANGE);
				g.fill(new Ellipse2D.Double(0, 15, 49, 19));
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(7, 34, 10, 8));
				g.fill(new Ellipse2D.Double(32, 34, 10, 8));
				g.setColor(Color.BLACK);
				g.drawString("D", 10, 29);
				break;
			
			case "right" :
				g.setColor(Color.ORANGE);
				g.fill(new Ellipse2D.Double(0, 15, 49, 19));
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(7, 34, 10, 8));
				g.fill(new Ellipse2D.Double(32, 34, 10, 8));
				g.setColor(Color.BLACK);
				g.drawString("D", 30, 29);
				break;
			}
		}
		g.translate(-this.getPosition().getX() * UI.CELL_SIDE_SIZE, -this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);

	}


	@Override
	public void progressCycle() {
		if (this.cyclesUntilTurn > 0) {
			this.cyclesUntilTurn--;
		}
		if (this.weaponCooldown > 0) {
			this.weaponCooldown--;
		}
	}

	@Override
	public void die() {
		this.lives--;
		if (this.lives == 0) {
			super.die();
			this.alive = false;
		} else {
			this.level.reset();
		}
	}

	/**
	 * @return if the player is alive (has positive lives remaining)
	 */
	public boolean alive() {
		return this.alive;
	}

	
	/**
	 * Attempts to fire the hero's weapon.
	 *
	 */
	public void fireWeapon() {
		if (this.weaponCooldown == 0) {
			if (this.direction != null) {
				Bullet newBullet = new Bullet(this.direction, this.level);
				Coordinate bulletSpawn = new Coordinate(0, 0);
				switch (this.direction) {
				case "up":

					bulletSpawn = new Coordinate(this.getPosition().getX(),
							this.getPosition().getY() - 1);
					break;
				case "down":

					bulletSpawn = new Coordinate(this.getPosition().getX(),
							this.getPosition().getY() + 1);
					break;
				case "left":

					bulletSpawn = new Coordinate(this.getPosition().getX() - 1,
							this.getPosition().getY());
					break;
				case "right":

					bulletSpawn = new Coordinate(this.getPosition().getX() + 1,
							this.getPosition().getY());
					break;
				}
				if (!(bulletSpawn.getX() > 9 || bulletSpawn.getX() < 0
						|| bulletSpawn.getY() > 9 || bulletSpawn.getY() < 0)) {
					if (this.getLevel().getComponentAt(bulletSpawn) == null) {
						newBullet.setPosition(bulletSpawn);
						this.level.setCoordinateTo(bulletSpawn, newBullet);
					} else if (this.getLevel().getComponentAt(bulletSpawn) instanceof Enemy) {
						this.getLevel().getComponentAt(bulletSpawn).die();
					}
				}
			}
			this.weaponCooldown = WEAPON_RECHARGE_CYCLES;
		}
	}

	/**
	 * Moves the hero one unit in its current direction of movement (if possible).
	 *
	 */
	public void takeTurn() {
		this.cyclesUntilTurn = CYCLES_PER_TURN;
		switch (this.direction) {
		case "up":
			if (this.getPosition().getY() > 0)
				this.moveTo(new Coordinate(this.getPosition().getX(), this
						.getPosition().getY() - 1));
			break;
		case "down":
			if (this.getPosition().getY() < 9)
				this.moveTo(new Coordinate(this.getPosition().getX(), this
						.getPosition().getY() + 1));
			break;
		case "left":
			if (this.getPosition().getX() > 0)
				this.moveTo(new Coordinate(this.getPosition().getX() - 1, this
						.getPosition().getY()));
			break;
		case "right":
			if (this.getPosition().getX() < 9)
				this.moveTo(new Coordinate(this.getPosition().getX() + 1, this
						.getPosition().getY()));
			break;
		}
	}

	/**
	 * Sets the player's direction (to move and shoot) to the specified direction:
	 * should be "up" "down" "right" or "left"
	 *
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;

	}

	/**
	 * @return the number of lives the player has remaining
	 */
	public int getLives() {
		return this.lives;
	}

}
