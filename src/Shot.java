import java.awt.Color;
import java.awt.Graphics;

/**
 * A class to define a shot on goal
 * The x position is taken from the center of the goal and
 * the y position is taken from the ground up, measurements are
 * in centimeters stored as a double
 * @author Jun Burden
 * @version 1.0
 */
public class Shot {
	private double x;
	private double y;
	private boolean goal;
	private int goalieTeam;
	
	private final int SIZE = 6;
	private final String csvSplitBy = ",";
	public static final String FILE_HEADER = "X,Y,goal,goalies team";
	
	public Shot(double x, double y, boolean goal, int goalieTeam) {
		this.x = x;
		this.y = y;
		this.goal = goal;
		this.goalieTeam = goalieTeam;
	}
	
	public Shot(String string){
		String[] elements = string.split(csvSplitBy);
	    this.x = Double.parseDouble(elements[0]);
	    this.y = Double.parseDouble(elements[1]);
	    this.goal = Boolean.parseBoolean(elements[2]);
	    this.goalieTeam = Integer.parseInt(elements[3]);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean wasGoal() {
		return goal;
	}
	
	public int getGoaliesTeam() {
		return goalieTeam;
	}
	
	/**
	 * Draws the shot on the graphics window
	 * @param g Graphics window to be drawn on
	 * @param scale of the window, cm/pixel
	 */
	public void draw(Graphics g, double scale, int xOffset, int yOffset) {
		int xPos = (int)(x/scale)+xOffset;
		int yPos = yOffset-(int)(y/scale);
		if(goal){
			g.setColor(Color.GREEN);
			g.drawOval(xPos-SIZE, yPos-SIZE, SIZE*2, SIZE*2);
		}
		else {
			g.setColor(Color.RED);
			g.drawLine(xPos-SIZE, yPos-SIZE, xPos+SIZE, yPos+SIZE);
			g.drawLine(xPos+SIZE, yPos-SIZE, xPos-SIZE, yPos+SIZE);
		}
	}
	
	public String toString(){
		return x+","+y+","+goal+","+goalieTeam;
	}
}
