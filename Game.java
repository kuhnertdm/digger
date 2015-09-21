import java.util.ArrayList;

/**
 * Holds information for the game itself
 * 
 * @author kuhnerdm
 * 
 */
public class Game {

	// Instance variables
	private int score;
	private Level level;
	private int levelNumber;
	private UI ui;
	private Player player;
	private boolean paused;

	/**
	 * Constructs a new Game object with no given values
	 */
	public Game() {
		this.score = 0;
		this.ui = new UI(this);
		this.level = new Level(1, this);
		this.levelNumber = 1;
		this.paused = false;
		this.ui.updateLevel();
		this.ui.createWindow();
	}

	/**
	 * Pauses the game if it is currently running, resumes the game if it is currently paused.
	 */
	public void togglePaused(){
		if(this.paused){
			this.paused = false;
		}else{
			this.paused = true;
		}
	}
	
	/**
	 * @return true if the game is paused.
	 */
	public boolean getPaused(){
		return this.paused;
	}
	
	
	/**
	 * Sets the current Player object
	 * 
	 * @param p
	 *            the Player object
	 */
	public void setPlayer(Player p) {
		this.player = p;
	}

	/**
	 * Sets the curent level number and Level object
	 * 
	 * @param levelNum
	 *            the number of the level
	 */
	public void setLevel(int levelNum) {
		if (levelNum > 0 && levelNum < 3) {
			this.levelNumber = levelNum;
			this.level = new Level(this.levelNumber, this);
			this.ui.getLevelComponent().setLevel(this.level);
		}
	}

	/**
	 * Progresses the game by one cycle
	 */
	public void progressCycle() {
		if (this.player.alive() && !this.paused) {
			ArrayList<Component> gameComponents = new ArrayList<Component>();
			for (int i = 0; i < this.level.getGrid().length; i++) {
				for (int j = 0; j < this.level.getGrid()[i].length; j++) {
					if (this.level.getGrid()[i][j] != null)
						gameComponents.add(this.level.getGrid()[i][j]);
				}
			}
			for (Component comp : gameComponents) {
				comp.progressCycle();
			}
			this.level.progressRespawns();
			this.ui.drawLevel();
			if (this.level.countEmeralds() == 0) {
				this.setLevel(this.levelNumber + 1);
			}
		}
	}

	/**
	 * Gets the current score
	 * 
	 * @return score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Gets the current level object
	 * 
	 * @return level
	 */
	public Level getLevel() {
		return this.level;
	}

	/**
	 * Gets the current level number
	 * 
	 * @return levelNumber
	 */
	public int getLevelNumber() {
		return this.levelNumber;
	}

	/**
	 * Gets the current UI object
	 * 
	 * @return ui
	 */
	public UI getUI() {
		return this.ui;
	}

	/**
	 * Gets the current Player object
	 * 
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}

}
