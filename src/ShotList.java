import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of shots
 * @author Jun Burden
 * @version 1.0
 */
public class ShotList {
	private List<Shot> shots;
	private int numGoals;
	private int numSaves;
	private int numShots;
	
	public ShotList() {
		shots = new ArrayList<Shot>();
		numGoals = 0;
		numSaves = 0;
		numShots = 0;
	}
	
	public void addShot(Shot shot) {
		if(shot == null) {
			throw new NullPointerException();
		}
		shots.add(shot);
		if(shot.wasGoal()) {
			numGoals++;
		}
		else {
			numSaves++;
		}
		numShots++;
		assert (numGoals + numSaves == numShots);
	}
	
	public List<Shot> getList() {
		return shots;
	}
	
	/**
	 * Draws the shots in the list on the graphics window
	 * @param g Graphics window to be drawn on
	 * @param scale of the window, cm/pixel
	 */
	public void draw(Graphics g, double scale, int xOffset, int yOffset) {
		for(Shot shot:shots) {
			shot.draw(g, scale, xOffset, yOffset);
		}
	}
	
	public String toString() {
		String output = "Out of " + numShots + " shots, there were " + numGoals
				      + " goals and " + numSaves + " saves\n";
		if(numShots > 0) {
			output += "Save rate of " + numSaves*100/numShots + "%\n";
		}
		return output;
	}
}
