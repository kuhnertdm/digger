import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyListener that listens for user input.
 *
 * @author grobleke.
 */
public class PlayerKeyListener implements KeyListener {
	
	private Player player;
	private Game game;
	
	/**
	 * Constructs a new PlayerKeyListener attatched to the specified player (the hero) and game.
	 *
	 * @param player
	 * @param game
	 */
	public PlayerKeyListener(Player player, Game game){
		this.player = player;
		this.game = game;
	}
	


	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT: //left arrow: move hero left
			this.player.setDirection("left");
			if(this.player.ready()){
				this.player.takeTurn();
			}
			break;
		case KeyEvent.VK_RIGHT: //right arrow: move hero right
			this.player.setDirection("right");
			if(this.player.ready()){
				this.player.takeTurn();
			}
			break;
		case KeyEvent.VK_UP: //up arrow: move hero up
			this.player.setDirection("up");
			if(this.player.ready()){
				this.player.takeTurn();
			}
			break;
		case KeyEvent.VK_DOWN: //down arrow: move hero down
			this.player.setDirection("down");
			if(this.player.ready()){
				this.player.takeTurn();
			}
			break;
		case KeyEvent.VK_SPACE: //space: hero fires weapon
			this.player.fireWeapon();
			break;
		case KeyEvent.VK_U: //u: move up a level
			this.game.setLevel(this.game.getLevelNumber() + 1);	
			break;
		case KeyEvent.VK_D: //d: move down a level
			this.game.setLevel(this.game.getLevelNumber() - 1);		
			break;
		case KeyEvent.VK_P: //p: move down a level
			this.game.togglePaused();		
			break;
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//do nothing
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//do nothing
	}
}