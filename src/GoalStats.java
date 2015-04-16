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

	private Shot clickShot;
	private boolean isGoal;
	private boolean showAll;
	
	private ShotSet shots;
	
	public GoalStats() {
		clickShot = null;
		isGoal = true;
		showAll = false;
		shots = new ShotSet();
	}

	@Override
	protected void onClick(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			isGoal = true;
		}
		else {
			isGoal = false;
		}
		clickShot = new Shot(e.getX(), e.getY(), isGoal);
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
			clickShot = new Shot(clickShot.getX(),clickShot.getY(),isGoal);
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
		g.fillRect(200, 200, 8, 208);
		g.fillRect(508, 200, 8, 208);
		g.fillRect(200, 200, 316, 8);
	}
	
	private void drawShots(Graphics g) {
		if(clickShot != null) {
		    clickShot.draw(g);
		}
		if(showAll) {
			shots.draw(g);
		}
	}
	
	public static void main(String[] args) {
		new GoalStats();
	}

}
