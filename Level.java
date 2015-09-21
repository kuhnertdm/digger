import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Contains information for a level in the game
 * 
 * @author zhoun
 * 
 */
public class Level {
	private static final int CYCLES_PER_RESPAWN = 250;
	private String file = "src/Level ";
	private char currentLevel[][] = new char[10][10];
	private Component[][] grid;
	private Game game;
	private HashMap<Component, Coordinate> resetters;
	private ArrayList<Character> respawners;
	private ArrayList<Coordinate> respawnerPositions;
	private int cyclesToNextRespawn;

	/**
	 * Constructs a new Level object for the specified game, for the
	 * "levelNum"th level.
	 * 
	 * @param levelNum
	 * @param game
	 */
	public Level(int levelNum, Game game) {
		this.cyclesToNextRespawn = CYCLES_PER_RESPAWN;
		this.game = game;
		Scanner scan;
		this.grid = new Component[10][10];
		this.resetters = new HashMap<Component, Coordinate>();
		this.respawners = new ArrayList<Character>();
		this.respawnerPositions = new ArrayList<Coordinate>();
		try {
			scan = new Scanner(new File(this.file + levelNum));// get level file in src
			int lineNo = 0;
			while (scan.hasNext()) {
				String line = scan.next();
				for (int i = 0; i < line.length(); i++) {
					this.currentLevel[i][lineNo] = line.charAt(i);
				}
				lineNo++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		loadLevel();
	}

	/**
	 * Makes progression of one cycles towards respawning any dead enemies
	 */
	public void progressRespawns(){
		if(!this.respawners.isEmpty()){
			if(this.cyclesToNextRespawn == 0){
				Character respawner = respawners.remove(0);
				Coordinate respawnerPosition = respawnerPositions.remove(0);
				if(respawner == 'n'){
					Nobbin newNobbin = new Nobbin(this, respawnerPosition);
					this.grid[respawnerPosition.getX()][respawnerPosition.getY()] = newNobbin;
					newNobbin.setPosition(respawnerPosition);
					this.resetters.put(newNobbin, respawnerPosition);
				} else if(respawner == 'h'){
					Hobbin newHobbin = new Hobbin(this, respawnerPosition);
					this.grid[respawnerPosition.getX()][respawnerPosition.getY()] = newHobbin;
					newHobbin.setPosition(respawnerPosition);
					this.resetters.put(newHobbin, respawnerPosition);
				}
				this.cyclesToNextRespawn = CYCLES_PER_RESPAWN; 
			} else {
				this.cyclesToNextRespawn--;
			}
		} else {
			this.cyclesToNextRespawn = CYCLES_PER_RESPAWN; 
		}
	}
	
	
	/**
	 * Clear the list of respawners.
	 */
	public void clearRespawners(){
		while(!this.respawners.isEmpty()){
			this.respawners.remove(0);
			this.respawnerPositions.remove(0);
		}
	}

	
	/**
	 * Fills the level's grid in accordance with the txt file for the level.
	 * 
	 */
	public void loadLevel() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Coordinate currentCoord = new Coordinate(i, j);
				if (this.currentLevel[i][j] == 'd') { //load dirt
					Dirt newDirt = new Dirt(this);
					this.grid[i][j] = newDirt;
					newDirt.setPosition(currentCoord);
				} else if (this.currentLevel[i][j] == 'p') { //load player
					if (this.game.getPlayer() == null) {
						Player newPlayer = new Player(this);
						this.grid[i][j] = newPlayer;
						this.game.setPlayer(newPlayer);
						this.game
								.getUI()
								.getGameFrame()
								.addKeyListener(
										new PlayerKeyListener(newPlayer,
												this.game));
						newPlayer.setPosition(currentCoord);
						this.resetters.put(newPlayer, currentCoord);
					} else {
						this.game.getPlayer().setLevel(this);
						this.grid[i][j] = this.game.getPlayer();
						this.game.getPlayer().setPosition(currentCoord);
						this.resetters.put(this.game.getPlayer(), currentCoord);
					}

				} else if (this.currentLevel[i][j] == 'e') { // load emerald
					Emerald newEmerald = new Emerald(this);
					this.grid[i][j] = newEmerald;
					newEmerald.setPosition(currentCoord);

				} else if (this.currentLevel[i][j] == 'g') { // load gold
					Gold newGold = new Gold(this);
					this.grid[i][j] = newGold;
					newGold.setPosition(currentCoord);
				} else if (this.currentLevel[i][j] == 'h') { //load hobbin
					Hobbin newHobbin = new Hobbin(this, currentCoord);
					this.grid[i][j] = newHobbin;
					newHobbin.setPosition(currentCoord);
					this.resetters.put(newHobbin, currentCoord);
				} else if (this.currentLevel[i][j] == 'n') { //load nobbin
					Nobbin newNobbin = new Nobbin(this, currentCoord);
					this.grid[i][j] = newNobbin;
					newNobbin.setPosition(currentCoord);
					this.resetters.put(newNobbin, currentCoord);
				}
			}
		}
	}

	/**
	 * Resets the hero and enemies to initial positions, without destroying tunnels.
	 *
	 */
	public void reset() {
		for (Component c : this.resetters.keySet()) {
			this.clearCoordinate(c.getPosition());
			this.setCoordinateTo(this.resetters.get(c), c);
			c.setPosition(this.resetters.get(c));
		}
		this.clearRespawners();
	}

	/**
	 * Gets the grid for the level
	 * 
	 * @return grid
	 */
	public Component[][] getGrid() {
		return this.grid;
	}

	/**
	 * Gets the component at a certain point in the grid
	 * 
	 * @param point
	 *            the point at which to find the component
	 * @return compone nt at point
	 */
	public Component getComponentAt(Coordinate point) {
		return this.grid[point.getX()][point.getY()];
	}

	/**
	 *
	 * @return the number of emeralds remaining in the level.
	 */
	public int countEmeralds() {
		int count = 0;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[0].length; j++) {
				if (this.grid[i][j] instanceof Emerald) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Sets the specified Coordinate of the level to the specified Component.
	 *
	 * @param point
	 * @param component
	 */
	public void setCoordinateTo(Coordinate point, Component component) {
		this.grid[point.getX()][point.getY()] = component;
	}

	/**
	 * Clears the specified Coordinate of the level.
	 *
	 * @param position
	 */
	public void clearCoordinate(Coordinate position) {
		this.grid[position.getX()][position.getY()] = null;

	}

	/**
	 * @return the game to which this level belongs
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param enemyType 
	 * @param respawnPosition
	 */
	public void addRespawner(char enemyType, Coordinate respawnPosition) {
		this.respawners.add(enemyType);
		this.respawnerPositions.add(respawnPosition);
	}

}
