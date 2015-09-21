import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * Holds information for the game's user interface
 * 
 * @author kuhnerdm
 * 
 */
public class UI {

	// Instance variables
	private Game game;
	/**
	 * Size in pixels of the sides of a cell.
	 */
	public static final double CELL_SIDE_SIZE = 50;
	private JFrame gameFrame;
	private LevelComponent levelComponent;
	private InfoComponent infoComponent;

	/**
	 * Constructs a new UI object for the given game
	 * 
	 * @param game 
	 */
	public UI(Game game) {
		this.gameFrame = new JFrame();
		this.game = game;
	}

	/**
	 * @return the JFrame displaying the game
	 */
	public JFrame getGameFrame(){
		return this.gameFrame;
	}
	
	/**
	 * Gets the current game object
	 * 
	 * @return game
	 */
	public Game getGame() {
		return this.game;
	}

	
	/**
	 * Creates the frame and JComponents for the game.
	 */
	public void createWindow() {
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setVisible(true);
		drawLevel();
		this.gameFrame.pack();
	}

	/**
	 * Updates the UI to display the game's current level.
	 *
	 */
	public void updateLevel(){
		this.levelComponent = new LevelComponent(this.game.getLevel());
		this.infoComponent = new InfoComponent(this.game.getPlayer());
		this.gameFrame.add(this.levelComponent, BorderLayout.CENTER);
		this.gameFrame.add(this.infoComponent, BorderLayout.SOUTH);	
	}
	
	/**
	 * @return the LevelComponent that displays the graphics of the level.
	 */
	public LevelComponent getLevelComponent(){
		return this.levelComponent;
	}
	
	/**
	 * Refreshes the display of level & info.
	 */
	public void drawLevel() {
		this.gameFrame.repaint();
	}

}
