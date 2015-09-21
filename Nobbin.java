import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;

/**
 * Contains information for the Nobbin object
 * 
 * @author kuhnerdm
 * 
 */
public class Nobbin extends Enemy {
	private Coordinate respawnPosition;

	/**
	 * Constructs a new Nobbin object with no given values
	 * 
	 * @param level
	 * @param respawnPosition
	 */
	public Nobbin(Level level, Coordinate respawnPosition) {
		super();
		this.respawnPosition = respawnPosition;
		this.level = level;
	}

	@Override
	public void die() {
		super.die();
		this.level.addRespawner('n', this.respawnPosition);
	}

	@Override
	public void drawTo(Graphics2D g) {
		g.translate(this.getPosition().getX() * UI.CELL_SIDE_SIZE, this
				.getPosition().getY() * UI.CELL_SIDE_SIZE);
		g.setColor(Color.BLACK);
		g.fill(new Rectangle2D.Double(0, 0, UI.CELL_SIDE_SIZE,
				UI.CELL_SIDE_SIZE));
		g.setColor(Color.BLUE);
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

		// Four places that the Nobbin has the ability to move to

		Component toLeft = null;
		Component toRight = null;
		Component above = null;
		Component below = null;

		try {
			toLeft = this.level.getGrid()[this.position.getX() - 1][this.position
					.getY()];
		} catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}

		try {
			toRight = this.level.getGrid()[this.position.getX() + 1][this.position
					.getY()];
		} catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}

		try {
			above = this.level.getGrid()[this.position.getX()][this.position
					.getY() - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
		}

		try {
			below = this.level.getGrid()[this.position.getX()][this.position
					.getY() + 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			// do nothing
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

		// Get number of possible directions (empty spaces)
		int possibleDirections = 0;
		if (toLeft == null) {
			possibleDirections++;
		}
		if (toRight == null) {
			possibleDirections++;
		}
		if (above == null) {
			possibleDirections++;
		}
		if (below == null) {
			possibleDirections++;
		}

		// Case: No directions available: Don't move
		if (possibleDirections == 0) {
			return null;
		}

		// Case: One direction available: Move to that space
		if (possibleDirections == 1) {
			if (toLeft == null) {
				return "left";
			}
			if (toRight == null) {
				return "right";
			}
			if (above == null) {
				return "up";
			}
			if (below == null) {
				return "down";
			}
		}
		// Determine vertical and horizontal distance from enemy to player
		Player player = this.getLevel().getGame().getPlayer();
		int horizontalSpaces = player.getPosition().getX()
				- this.getPosition().getX();
		int verticalSpaces = player.getPosition().getY()
				- this.getPosition().getY();

		// Case: Two directions available: Determine which direction gets you
		// closer to the player. If each gets you closer, pick which which
		// direction has a lower vert/hori distance.

		if (possibleDirections == 2) {

			// Handle when opposite sides are open
			if (toLeft == null && toRight == null) {
				if (horizontalSpaces >= 0) {
					return "right";
				} else {
					return "left";
				}
			}
			if (above == null && below == null) {
				if (verticalSpaces >= 0) {
					return "down";
				} else {
					return "up";
				}
			}

			// Handle other cases

			// Above-right
			if (above == null && toRight == null) {
				if (horizontalSpaces == 0) {
					return "up";
				}
				if (verticalSpaces == 0) {
					return "right";
				}
				if (horizontalSpaces > 0 && verticalSpaces > 0) {
					return "right";
				}
				if (horizontalSpaces > 0 && verticalSpaces < 0) {
					if (-verticalSpaces > horizontalSpaces) {
						return "right";
					} else {
						return "up";
					}
				}
				if (horizontalSpaces < 0 && verticalSpaces > 0) {
					if (verticalSpaces > -horizontalSpaces) {
						return "right";
					} else {
						return "up";
					}
				}
				if (horizontalSpaces < 0 && verticalSpaces < 0) {
					return "up";
				}
			}

			// Below-right
			if (toRight == null && below == null) {
				if (horizontalSpaces == 0) {
					return "down";
				}
				if (verticalSpaces == 0) {
					return "right";
				}
				if (horizontalSpaces > 0 && verticalSpaces > 0) {
					if (verticalSpaces > horizontalSpaces) {
						return "down";
					} else {
						return "right";
					}
				}
				if (horizontalSpaces > 0 && verticalSpaces < 0) {
					return "right";
				}
				if (horizontalSpaces < 0 && verticalSpaces > 0) {
					return "down";
				}
				if (horizontalSpaces < 0 && verticalSpaces < 0) {
					if (verticalSpaces < horizontalSpaces) {
						return "right";
					} else {
						return "down";
					}
				}
			}

			// Below-left
			if (below == null && toLeft == null) {
				if (horizontalSpaces == 0) {
					return "down";
				}
				if (verticalSpaces == 0) {
					return "left";
				}
				if (horizontalSpaces > 0 && verticalSpaces > 0) {
					return "down";
				}
				if (horizontalSpaces > 0 && verticalSpaces < 0) {
					if (-verticalSpaces > horizontalSpaces) {
						return "left";
					} else {
						return "down";
					}
				}
				if (horizontalSpaces < 0 && verticalSpaces > 0) {
					if (verticalSpaces > -horizontalSpaces) {
						return "down";
					} else {
						return "left";
					}
				}
				if (horizontalSpaces < 0 && verticalSpaces < 0) {
					return "left";
				}
			}

			// Above-left
			if (toLeft == null && above == null) {
				if (horizontalSpaces == 0) {
					return "up";
				}
				if (verticalSpaces == 0) {
					return "left";
				}
				if (horizontalSpaces > 0 && verticalSpaces > 0) {
					if (verticalSpaces > horizontalSpaces) {
						return "left";
					} else {
						return "up";
					}
				}
				if (horizontalSpaces > 0 && verticalSpaces < 0) {
					return "up";
				}
				if (horizontalSpaces < 0 && verticalSpaces > 0) {
					return "left";
				}
				if (horizontalSpaces < 0 && verticalSpaces < 0) {
					if (verticalSpaces > horizontalSpaces) {
						return "up";
					} else {
						return "left";
					}
				}
			}
		}

		// Case: Three directions available

		if (possibleDirections == 3) {

			if (!(below == null)) {
				if (verticalSpaces >= 0) {
					if (horizontalSpaces > 0) {
						return "right";
					} else {
						return "left";
					}
				} else {
					if (-verticalSpaces > Math.abs(horizontalSpaces)) {
						return "up";
					} else {
						if (horizontalSpaces > 0) {
							return "right";
						} else {
							return "left";
						}
					}
				}
			}

			if (!(above == null)) {
				if (verticalSpaces <= 0) {
					if (horizontalSpaces > 0) {
						return "right";
					} else {
						return "left";
					}
				} else {
					if (verticalSpaces > Math.abs(horizontalSpaces)) {
						return "down";
					} else {
						if (horizontalSpaces > 0) {
							return "right";
						} else {
							return "left";
						}
					}
				}
			}

			if (!(toRight == null)) {
				if (horizontalSpaces <= 0) {
					if (verticalSpaces > 0) {
						return "down";
					} else {
						return "up";
					}
				} else {
					if (horizontalSpaces > Math.abs(verticalSpaces)) {
						return "left";
					} else {
						if (verticalSpaces > 0) {
							return "down";
						} else {
							return "up";
						}
					}
				}
			}

			if (!(toLeft == null)) {
				if (horizontalSpaces >= 0) {
					if (verticalSpaces > 0) {
						return "down";
					} else {
						return "up";
					}
				} else {
					if (-horizontalSpaces > Math.abs(verticalSpaces)) {
						return "right";
					} else {
						if (verticalSpaces > 0) {
							return "down";
						} else {
							return "up";
						}
					}
				}
			}
		}

		// Case: All four positions available

		if (possibleDirections == 4) {

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

		}

		return null;
	}
}
