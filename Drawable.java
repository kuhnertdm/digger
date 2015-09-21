import java.awt.Graphics2D;

/**
 * Contains an interface method for any object that can be drawn
 * 
 * @author grobleke. Created Oct 28, 2014.
 */
public interface Drawable {
	/**
	 * Draws the object to the specified graphics object.
	 *
	 * @param g
	 */
	void drawTo(Graphics2D g);
}
