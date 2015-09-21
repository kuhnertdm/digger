/**
 * Contains two values for the grid, an X int and a Y int
 * 
 * @author grobleke. Created Oct 28, 2014.
 */
public class Coordinate {
	private int x;
	private int y;

	/**
	 * Constructs a new Coordinate object with the given X and Y values
	 * 
	 * @param i
	 *            the X value
	 * @param j
	 *            the Y value
	 */
	public Coordinate(int i, int j) {
		this.x = i;
		this.y = j;
	}

	/**
	 * Sets the X value of the Coordinate
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the Y value of the Coordinate
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the X value of the Coordinate
	 * 
	 * @return x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns the Y value of the Coordinate
	 * 
	 * @return y
	 */
	public int getY() {
		return this.y;
	}

}
