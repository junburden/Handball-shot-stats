import java.awt.Color;
import java.awt.Graphics;

/**
 * A class to define a shot on goal
 * The x position is taken from the center of the goal and
 * the y position is taken from the ground up
 * @author Jun Burden
 * @version 1.0
 */
public class Shot {
	private int x;
	private int y;
	private boolean goal;
	
	private final int SIZE = 4;
	
	public Shot(int x, int y, boolean goal) {
		this.x = x;
		this.y = y;
		this.goal = goal;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean wasGoal() {
		return goal;
	}
	
	public void draw(Graphics g) {
		if(goal){
			g.setColor(Color.GREEN);
			g.drawOval(x-SIZE, y-SIZE, SIZE*2, SIZE*2);
		}
		else {
			g.setColor(Color.RED);
			g.drawLine(x-SIZE, y-SIZE, x+SIZE, y+SIZE);
			g.drawLine(x+SIZE, y-SIZE, x-SIZE, y+SIZE);
		}
	}
}
