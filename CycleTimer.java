import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the timing of cycles
 * 
 * @author kuhnerdm
 * 
 */
public class CycleTimer implements ActionListener {

	// Instance variables
	private Game game;

	/**
	 * Constructs a new CycleTimer object with the given Game object
	 * 
	 * @param game
	 */
	public CycleTimer(Game game) {
		this.game = game;
	}

	/**
	 * Handles a timer event by progressing the cycle of the game
	 * 
	 * @param e
	 *            the ActionEvent called
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.game.progressCycle();
	}

}
