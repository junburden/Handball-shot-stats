import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
/**
 * @author Jun Burden
 *
 */
public class CsvFileHandler {
     
    //CSV file header, column titles
    private static final String FILE_HEADER = "X,Y,goal";
 
    public static void writeCsvFile(String fileName, ShotList shots) {
                 
        try {
        	// Open file, if it already exists check if you want to override it
        	File file = new File(fileName);
        	boolean overrideFile = true;
        	if (file.createNewFile()){
    	        System.out.println("File is created!");
    	    }else{
    	        System.out.println("File already exists.");
    	        int dialogResult = JOptionPane.showConfirmDialog (null, "This file already exists, do you want to override it?","Warning",JOptionPane.YES_NO_OPTION);
    	        if(dialogResult == JOptionPane.NO_OPTION){
    	            overrideFile = false;
    	        }
    	    }
        	
        	// Write stats to the file
        	if(overrideFile) {
	        	PrintWriter writer = new PrintWriter(fileName, "UTF-8");
	        	writer.println(FILE_HEADER);
	        	for(Shot shot:shots.getList()) {
	        		writer.println(shot.getX()+","+shot.getY()+","+shot.wasGoal());
	        	}
	        	writer.close();
        	}
        } catch (Exception e) {
  	      e.printStackTrace();
  	    }
    }
    
    public static void readCsvFile(File file, ShotList shots) {
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
    	
    	try {
    		br = new BufferedReader(new FileReader(file));
    		// Ignore header line
    		br.readLine();
    		while ((line = br.readLine()) != null) {
			    String[] elements = line.split(cvsSplitBy);
			    double x = Double.parseDouble(elements[0]);
			    double y = Double.parseDouble(elements[1]);
			    boolean goal = Boolean.parseBoolean(elements[2]);
			    shots.addShot(new Shot(x,y,goal));
		    }
    	} catch(Exception e) {
    		
    	}finally {
    		if(br != null) {
    		    try {
    		        br.close();
    		    } catch(Exception e) {
    		    }
    		}
    	}
    }
}