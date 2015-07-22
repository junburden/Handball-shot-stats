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
	
	private final int SIZE = 4;
	
	public Shot(double x, double y, boolean goal) {
		this.x = x;
		this.y = y;
		this.goal = goal;
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
}
