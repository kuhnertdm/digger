/**
 * Contains information for the Enemy object, serving as the super class for the
 * Nobbin and Hobbin
 * 
 * @author kuhnerdm
 * 
 */
public abstract class Enemy extends Component {
	private static final int CYCLES_PER_TURN = 28;
	private int cyclesUntilTurn;

	/**
	 * Constructs a new Enemy object with the given respawn position
	 * 
	 * @param respawnPosition 
	 */
	public Enemy() {
		super();
		this.cyclesUntilTurn = CYCLES_PER_TURN;
	}

	/**
	 * Handles a collision with the given Component object
	 * 
	 * @param component
	 *            the Component to collide with
	 */
	@Override
	public void collideWith(Component component) {
		if (component instanceof Player || component instanceof Bullet) {
			this.die();
			component.die();
		} else {
			Coordinate destination = component.getPosition();
			component.die();
			this.getLevel().setCoordinateTo(destination, this);
			this.getLevel().clearCoordinate(this.getPosition());
			this.setPosition(destination);
		}
	}

	
	/**
	 * Handles a cycle change in the game
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
	 * Moves the Enemy in the direction given by getDirectionToMove
	 */
	public void takeTurn() {
		switch (this.getDirectionToMove()) {
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

	// Abstract methods

	/**
	 * @return the direction the enemy's AI decides it should move
	 */
	public abstract String getDirectionToMove();
}
