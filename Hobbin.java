import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Contains information for the Hobbin object
 * 
 * @author kuhnerdm
 * 
 */
public class Hobbin extends Enemy {
	private Coordinate respawnPosition;
	
	/**
	 * Constructs a new Hobbin object with no values given
	 * 
	 * @param level
	 * @param respawnPosition 
	 */
	public Hobbin(Level level, Coordinate respawnPosition) {
		super();
		this.respawnPosition = respawnPosition;
		this.level = level;

	}
	
	@Override
	public void die(){
		super.die();
		this.level.addRespawner('h', this.respawnPosition);
	}


	@Override
	public void drawTo(Graphics2D g) {
		g.translate(this.getPosition().getX() * UI.CELL_SIDE_SIZE, this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
		g.setColor(Color.BLACK);
		g.fill(new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE,
				UI.CELL_SIDE_SIZE));
		g.setColor(Color.RED);
		g.draw(new Ellipse2D.Double(0, 18, 50, 25));
		g.draw(new Ellipse2D.Double(14, 3, 22, 15));
		g.draw(new Ellipse2D.Double(20, 7, 2, 2));
		g.draw(new Ellipse2D.Double(27, 7, 2, 2));
		g.draw(new Arc2D.Double(22, 12, 6, 3, 0, 180, Arc2D.OPEN));
		g.draw(new Arc2D.Double(2, 41, 9, 8, 90, 180, Arc2D.OPEN));
		g.draw(new Arc2D.Double(38, 41, 9, 8, -90, 180, Arc2D.OPEN));
		g.translate(-this.getPosition().getX() * UI.CELL_SIDE_SIZE, -this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
	}

	@Override
	public String getDirectionToMove() {
		// Four places that the Hobbin has the ability to move to

		Component toLeft = null;
		Component toRight = null;
		Component above = null;
		Component below = null;

		try {
			toLeft = this.level.getGrid()[this.position.getX() - 1][this.position.getY()];
		} catch(ArrayIndexOutOfBoundsException e) {
			//do nothing
		}
		
		try {
			toRight = this.level.getGrid()[this.position.getX() + 1][this.position.getY()];
		} catch(ArrayIndexOutOfBoundsException e) {
			//do nothing
		}
		
		try {
			above = this.level.getGrid()[this.position.getX()][this.position.getY() - 1];
		} catch(ArrayIndexOutOfBoundsException e) {
			//do nothing
		}
		
		try {
			below = this.level.getGrid()[this.position.getX()][this.position.getY() + 1];
		} catch(ArrayIndexOutOfBoundsException e) {
			//do nothing
		}

		// Prioritize killing the player
		if (toLeft instanceof Player) {
			return "left";
		}
		if (toRight instanceof Player) {
			return "right";
		}
		if (above instanceof Player) {
			return "up";
		}
		if (below instanceof Player) {
			return "down";
		}

		// Determine vertical and horizontal distance from enemy to player
		Player player = this.getLevel().getGame().getPlayer();
		int horizontalSpaces = player.getPosition().getX()
				- this.getPosition().getX();
		int verticalSpaces = player.getPosition().getY()
				- this.getPosition().getY();

		// Handle vertical/horizontal cases
		if (horizontalSpaces == 0 && verticalSpaces > 0) {
			return "down";
		}
		if (horizontalSpaces == 0 && verticalSpaces < 0) {
			return "up";
		}
		if (verticalSpaces == 0 && horizontalSpaces > 0) {
			return "right";
		}
		if (verticalSpaces == 0 && horizontalSpaces < 0) {
			return "left";
		}

		// Handle other cases
		if (horizontalSpaces > 0 && verticalSpaces > 0) {
			if (horizontalSpaces > verticalSpaces) {
				return "down";
			} else {
				return "right";
			}
		}
		if (horizontalSpaces > 0 && verticalSpaces < 0) {
			if (horizontalSpaces > -verticalSpaces) {
				return "up";
			} else {
				return "right";
			}
		}
		if (horizontalSpaces < 0 && verticalSpaces > 0) {
			if (-horizontalSpaces > verticalSpaces) {
				return "down";
			} else {
				return "left";
			}
		}
		if (horizontalSpaces < 0 && verticalSpaces < 0) {
			if (-horizontalSpaces > -verticalSpaces) {
				return "up";
			} else {
				return "left";
			}
		}

		return null;
	}

}
