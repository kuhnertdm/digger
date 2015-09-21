import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

/**
 * Runs the Digger program.
 *
 * @author grobleke.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		class CycleButton extends JButton implements ActionListener{
			private Game g;
			
			public CycleButton(Game g){
				this.g = g;
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				this.g.progressCycle();
			}
			
		}
		
		Game currentGame = new Game();
		
		Timer cycleTimer = new Timer(15, new CycleButton(currentGame));
		cycleTimer.start();
		
		
	}

	
	
}
