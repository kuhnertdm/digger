import java.awt.geom.Point2D;


/**
 * An interface for game components that can move.
 *
 * @author grobleke.
 *         Created Oct 28, 2014.
 */
public interface Movable {
	/**
	 * @return the position of the object in its level's grid
	 */
	Coordinate getPosition();

	/**
	 * Moves to the specified coordinate.
	 *
	 * @param destination
	 */
	void moveTo(Coordinate destination);
}
