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
    
    /**
     * Writes shot statistics to a given csv file. If the file already exists there
     * will be a warning message before overwriting the file
     * @param fileName of the csv file to be written to
     * @param shots to be written to the file
     */
    public static void writeCsvFile(String fileName, ShotList shots) {
                 
        try {
        	// Open file, if it already exists check if you want to override it
        	File file = new File(fileName);
        	boolean overrideFile = true;
        	if (!file.createNewFile()){
    	        int dialogResult = JOptionPane.showConfirmDialog (null, "This file already exists, do you want to overwrite it?","Warning",JOptionPane.YES_NO_OPTION);
    	        if(dialogResult == JOptionPane.NO_OPTION){
    	            overrideFile = false;
    	        }
    	    }
        	
        	// Write stats to the file
        	if(overrideFile) {
	        	PrintWriter writer = new PrintWriter(fileName, "UTF-8");
	        	writer.println(Shot.FILE_HEADER);
	        	for(Shot shot:shots.getList()) {
	        		writer.println(shot.toString());
	        	}
	        	writer.close();
        	}
        } catch (Exception e) {
  	      e.printStackTrace();
  	    }
    }
    
    /**
     * Loads shot statistics from a csv file. The statistics from the file will be added
     * to the list, the existing statistics will not be deleted. Assumes the file has
     * correct formatting
     * @param file to load the statistics from
     * @param listOfShots to add stats too
     */
    public static void readCsvFile(File file, ShotList listOfShots) {
    	BufferedReader br = null;
    	String line = "";
    	
    	try {
    		br = new BufferedReader(new FileReader(file));
    		// Ignore header line
    		br.readLine();
    		while ((line = br.readLine()) != null) {
			    listOfShots.addShot(new Shot(line));
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