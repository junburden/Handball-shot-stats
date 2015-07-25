import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * This is a GUI class which can be used to display shots at goal
 * 
 * @author Jun Burden
 * @version 1.0
 */
public abstract class GUI {

	/**
	 * Is called when the drawing area is redrawn and performs all the logic for
	 * the actual drawing, which is done with the passed Graphics object.
	 */
	protected abstract void redraw(Graphics g);

	/**
	 * Is called when the mouse is clicked (actually, when the mouse is
	 * released), and is passed the MouseEvent object for that click.
	 */
	protected abstract void onClick(MouseEvent e);
	
	/**
	 * Is called when a key is pressed (actually, when the key is
	 * released), and is passed the KeyEvent object.
	 */
	protected abstract void onKeyPress(KeyEvent e);
	
	/**
	 * Is called when the goal or save buttons are pressed to indicate
	 * whether it is a goal or not
	 */
	protected abstract void setGoal(boolean b);
	
	/**
	 * Is called when the show all tick box is changed to indicate
	 * whether to display all of the goals or not
	 */
	protected abstract void setShowAll(boolean b);
	
	/**
	 * Is called when the enter button is pressed to indicate
	 * the shot is to be saved
	 */
	protected abstract void submitShot();
	
	/**
	 * Load a csv file containing shot statistic information
	 */
	protected abstract void loadData();
	
	/**
	 * Save shot statistic information to csv file
	 */
	protected abstract void saveData();
	
	/**
	 * Clear shot statistic information
	 */
	protected abstract void clearData();
	
	/**
	 * @return the JTextArea at the bottom of the screen for output.
	 */
	public JTextArea getTextOutputArea() {
		return textOutputArea;
	}

	/**
	 * @return the dimensions of the drawing area.
	 */
	public Dimension getDrawingAreaDimension() {
		return drawing.getSize();
	}

	/**
	 * Redraws the window (including drawing pane). This is already done
	 * whenever a button is pressed or the search box is updated, so you
	 * probably won't need to call this.
	 */
	public void redraw() {
		frame.repaint();
	}

	private static final int DEFAULT_DRAWING_HEIGHT = 480;
	private static final int DEFAULT_DRAWING_WIDTH = 640;
	private static final int TEXT_OUTPUT_ROWS = 5;

	protected JFrame frame;

	private JPanel controls;
	private JComponent drawing;
	private JTextArea textOutputArea;
	
	public GUI() {
		initialise();
	}

	@SuppressWarnings("serial")
	private void initialise() {
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});
		
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				loadData();
				redraw();
			}
		});
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				saveData();
				redraw();
			}
		});
		
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				clearData();
				redraw();
			}
		});

		JButton goal = new JButton("Goal");
		goal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				setGoal(true);
				redraw();
			}
		});
		
		JButton noGoal = new JButton("No Goal");
		noGoal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				setGoal(false);
				redraw();
			}
		});
		
		JButton submit = new JButton("Enter");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				submitShot();
				redraw();
			}
		});
		
		JCheckBox showAll = new JCheckBox("Show all");
		showAll.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getSource()==showAll){
	                if(showAll.isSelected()) {
	                    setShowAll(true);
	                } else {
	                	setShowAll(false);
	                }
	                redraw();
	            }
	        }
		});

		controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		controls.setBorder(edge);
		controls.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				onKeyPress(e);
				
			}
		});
		
		JPanel loadSaveClear = new JPanel();
		loadSaveClear.setMaximumSize(new Dimension(150, 50));
		loadSaveClear.setLayout(new GridLayout(2, 2));
		loadSaveClear.add(load);
		loadSaveClear.add(save);
		loadSaveClear.add(clear);
		
		
		JPanel goalNoGoal = new JPanel();
		goalNoGoal.setMaximumSize(new Dimension(150, 50));
		goalNoGoal.setLayout(new GridLayout(1, 2));
		goalNoGoal.add(goal);
		goalNoGoal.add(noGoal);
		
		JPanel enterShow = new JPanel();
		enterShow.setMaximumSize(new Dimension(150, 100));
		enterShow.setLayout(new GridLayout(2, 1));
		enterShow.add(submit);
		enterShow.add(showAll);
		
		JPanel loadQuit = new JPanel();
		loadQuit.setMaximumSize(new Dimension(50, 50));
		loadQuit.setLayout(new GridLayout(1, 1));
		loadQuit.add(quit);
		
		controls.add(loadSaveClear);
		controls.add(Box.createRigidArea(new Dimension(0, 50)));
		controls.add(goalNoGoal);
		controls.add(Box.createRigidArea(new Dimension(0, 50)));
		controls.add(enterShow);

		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};
		drawing.setPreferredSize(new Dimension(DEFAULT_DRAWING_WIDTH,
				DEFAULT_DRAWING_HEIGHT));
		drawing.setVisible(true);

		drawing.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				onClick(e);
				redraw();
			}
		});
		
		textOutputArea = new JTextArea(TEXT_OUTPUT_ROWS, 0);
		textOutputArea.setLineWrap(true);
		textOutputArea.setWrapStyleWord(true);
		textOutputArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textOutputArea);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(scroll);
		bottom.add(loadQuit);

		frame = new JFrame("Mapper");
		// this makes the program actually quit when the frame's close button is
		// pressed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(controls, BorderLayout.EAST);
		frame.add(drawing, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH);
		
		frame.getRootPane().setDefaultButton(submit);

		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
	}
}

// code for COMP261 assignments
