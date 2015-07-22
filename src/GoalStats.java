import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private final int GOAL_POS_X = 350;
	private final int GOAL_POS_Y = 400;
	
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
		double x = (e.getX()-GOAL_POS_X)*CM_PER_PIXELS;
		double y = (GOAL_POS_Y-e.getY())*CM_PER_PIXELS;
		clickShot = new Shot(x, y, isGoal);
	}
	
	@Override
	protected void onKeyPress(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
			saveShot();
		}
	}
	
	@Override
	protected void setGoal(boolean isGoal) {
		if(clickShot != null) {
			clickShot = new Shot(clickShot.getX(), clickShot.getY(), isGoal);
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
	protected void loadData() {
		// Choose a file
		JFileChooser fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		fc.setFileFilter(new FileNameExtensionFilter("CSV file","csv"));
		int returnValue = fc.showOpenDialog(frame);
		File file = fc.getSelectedFile();
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
		    CsvFileHandler.readCsvFile(file, shots);
		}
	}

	@Override
	protected void saveData() {
		JFileChooser fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		int returnValue = fc.showSaveDialog(frame);
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			String fileName = fc.getSelectedFile().getAbsolutePath()+".csv";
		    CsvFileHandler.writeCsvFile(fileName, shots);
		}
	}
	
	@Override
	protected void clearData() {
		int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to clear all of the shooting statistics?","Warning",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            shots = new ShotList();
            clickShot = null;
        }
	}
	
	@Override
	protected void redraw(Graphics g) {
		drawGoal(g);
		drawShots(g);
		getTextOutputArea().setText(shots.toString());
	}
	
	private void drawGoal(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(GOAL_POS_X-GOAL_WIDTH/2-POST_THICKNESS, GOAL_POS_Y-GOAL_HEIGHT, POST_THICKNESS, GOAL_HEIGHT+POST_THICKNESS);
		g.fillRect(GOAL_POS_X+GOAL_WIDTH/2+POST_THICKNESS, GOAL_POS_Y-GOAL_HEIGHT, POST_THICKNESS, GOAL_HEIGHT+POST_THICKNESS);
		g.fillRect(GOAL_POS_X-GOAL_WIDTH/2-POST_THICKNESS, GOAL_POS_Y-GOAL_HEIGHT, GOAL_WIDTH+POST_THICKNESS*2, POST_THICKNESS);
	}
	
	private void drawShots(Graphics g) {
		if(clickShot != null) {
		    clickShot.draw(g, CM_PER_PIXELS, GOAL_POS_X, GOAL_POS_Y);
		}
		if(showAll) {
			shots.draw(g, CM_PER_PIXELS, GOAL_POS_X, GOAL_POS_Y);
		}
	}
	
	public static void main(String[] args) {
		new GoalStats();
	}
}
