import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Extends the GUI to display and record shots at a goal
 * @author Jun Burden
 * @version 1.0
 */
public class GoalStats extends GUI{

	private final double CM_PER_PIXELS = 1; 
	private final int GOAL_HEIGHT = (int) (200/CM_PER_PIXELS);
	private final int GOAL_WIDTH = (int) (300/CM_PER_PIXELS);
	private final int POST_THICKNESS = (int) (8/CM_PER_PIXELS);
	private final int GOAL_POS_X = 200;
	private final int GOAL_POS_Y = 200;
	
	private Shot clickShot;
	private boolean isGoal;
	private boolean showAll;
	
	private ShotList shots;
	
	public GoalStats() {
		clickShot = null;
		isGoal = true;
		showAll = false;
		shots = new ShotList();
	}

	@Override
	protected void onClick(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			isGoal = true;
		}
		else {
			isGoal = false;
		}
		clickShot = new Shot(e.getX()*CM_PER_PIXELS, e.getY()*CM_PER_PIXELS, isGoal);
	}
	
	@Override
	protected void onKeyPress(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
			saveShot();
		}
	}
	
	@Override
	protected void setGoal(boolean goal) {
		isGoal = goal;
		if(clickShot != null) {
			clickShot = new Shot(clickShot.getX()*CM_PER_PIXELS,clickShot.getY()*CM_PER_PIXELS,isGoal);
		}
	}
	@Override
	protected void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
	
	@Override
	protected void saveShot() {
		shots.addShot(clickShot);
	}
	
	@Override
	protected void redraw(Graphics g) {
		drawGoal(g);
		drawShots(g);
		getTextOutputArea().setText(shots.toString());
	}
	
	private void drawGoal(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(GOAL_POS_X, GOAL_POS_Y, POST_THICKNESS, GOAL_HEIGHT+POST_THICKNESS);
		g.fillRect(GOAL_POS_X+GOAL_WIDTH+POST_THICKNESS, GOAL_POS_Y, POST_THICKNESS, GOAL_HEIGHT+POST_THICKNESS);
		g.fillRect(GOAL_POS_X, GOAL_POS_Y, GOAL_WIDTH+POST_THICKNESS*2, POST_THICKNESS);
	}
	
	private void drawShots(Graphics g) {
		if(clickShot != null) {
		    clickShot.draw(g, CM_PER_PIXELS);
		}
		if(showAll) {
			shots.draw(g, CM_PER_PIXELS);
		}
	}
	
	public static void main(String[] args) {
		new GoalStats();
	}

}
