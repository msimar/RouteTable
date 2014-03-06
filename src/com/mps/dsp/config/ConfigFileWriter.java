package com.mps.dsp.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.mps.dsp.util.Logger;

/**
 * File Writer to configure nodelist.txt file. It build
 * nodelist.txt file as per the given set of template.
 * The template is organized as :
 * <Host Index> <Host IPAddress> <Host PortNumber>
 * 
 * @author msingh
 *
 */
public class ConfigFileWriter {

	private final String TAG = ConfigFileWriter.class.getSimpleName();
	
	/**
	 * Write to the nodelist.txt configuration file.
	 */
	public void writeToConfigFile() {
		Logger.d(TAG, "writeToConfigFile() ");
		try {
			 
			String workingDir = System.getProperty("user.dir");
			String packagePath = "/src/com/mps/dsp/config/";
			
			//System.out.println(workingDir);
			
			String content = "localhost";
 
			File file = new File(workingDir + packagePath + "/nodelist.txt");
			
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int indx = 0 ; indx < Configuration.MAX_NODE ; indx++)
				bw.write(indx + " " + content + " " + (40000 + indx) + "\n");
			
			bw.close();
 
			//System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
