
/**
 * Contains information for the Component object
 * 
 * @author grobleke. Created Oct 28, 2014.
 */
public abstract class Component implements Movable, Drawable {

	// Instance variables
	/**
	 * The position of this Component in the level's grid.
	 */
	protected Coordinate position;

	/**
	 * The level to which this Component belongs.
	 */
	protected Level level;

	/**
	 * Returns the level that this Component belongs to
	 * 
	 * @return level
	 */
	public Level getLevel() {
		return this.level;
	}

	/**
	 * Handles moving to another space. If the space is null, this method will
	 * handle the moving. If the space is not null, the collideWith method must
	 * handle movement.
	 * 
	 * @param destination
	 *            the space to move to
	 */
	@Override
	public void moveTo(Coordinate destination) {
		if (this.getLevel().getComponentAt(destination) == null) {
			this.getLevel().setCoordinateTo(destination, this);
			this.getLevel().clearCoordinate(this.getPosition());
			this.setPosition(destination);
		} else {
			this.collideWith(this.level.getComponentAt(destination));
		}

	}

	/**
	 * Sets the position of the Component on the grid
	 * 
	 * @param newPosition
	 *            the position to move the Component to
	 */
	public void setPosition(Coordinate newPosition) {
		this.position = newPosition;
	}

	/**
	 * Gets the current position of the Component on the grid
	 * 
	 * @return position
	 */
	@Override
	public Coordinate getPosition() {
		return this.position;
	}

	/**
	 * Removes the Component from the grid
	 */
	public void die() {
		this.getLevel().clearCoordinate(this.getPosition());
	}

	// Abstract methods
	
	/**
	 * Handles a collision between this Component and the other specified Component.
	 *
	 * @param component
	 */
	public abstract void collideWith(Component component);

	/**
	 * Handles a single progression of the game's cycle.
	 */
	public abstract void progressCycle();
}
