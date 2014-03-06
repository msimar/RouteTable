package com.mps.dsp.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;
import com.mps.dsp.util.Logger;

/**
 * File Reader to configure nodelist.txt file. It parse 
 * the file and build nodes data stucture for the 
 * overlay networks. Nodes are stored in a NodeRegistry
 * class
 * 
 * @author msingh
 *
 */
public class ConfigFileReader {
	
	/**
	 * The logger tag for ConfigFileReader class
	 */
	private final String TAG = ConfigFileReader.class.getSimpleName();
	
	/**
	 * This method parse the configuration file and transform content of
	 * configuration file into set of Nodes. These compiled Nodes are stored
	 * into the the Resource class.
	 */
	public boolean parseConfigFile(String fileName){
		Logger.d(TAG, "parseConfigFile() ");

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("./" + fileName);
		
		try(BufferedReader br = new BufferedReader(new InputStreamReader(input))) {

		    for(String line; (line = br.readLine()) != null; ) {
		    	buildNodes(line);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Logger.d(TAG, "Total Nodes in System : " + NodeRegistry.getInstance().getNodes().size());
		
		if(NodeRegistry.getInstance().getNodes().size() > 0) return true;
		
		return false;
	}

	/**
	 * This method parse the configuration file and transform content of
	 * configuration file into set of Nodes. These compiled Nodes are stored
	 * into the the Resource class.
	 */
	public boolean parseConfigFile() {
		Logger.d(TAG, "writeToConfigFile() ");

		String workingDir = System.getProperty("user.dir");
		String packagePath = "/src/com/mps/dsp/config/";

		Logger.d(TAG, "workingDir : " + workingDir ); 

		File file = new File(workingDir + packagePath + "/nodelist.txt");
		
		System.out.println(file);
		System.out.println(file.exists());

		// if file does not exists, show message
		if (!file.exists()) {
			System.err.println("nodelist.txt does not exist!!, Configure nodelist.txt file.");
		}

		if (file.exists()) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)))) {

				for (String line; (line = br.readLine()) != null;) {
					buildNodes(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Logger.d(TAG, "Total Nodes in System : " + NodeRegistry.getInstance().getNodes().size());
		
		if(NodeRegistry.getInstance().getNodes().size() > 0) return true;
		
		return false;
	}

	/**
	 * This method build Nodes for converting each lines String into tokens.
	 * Each token represent Node property which is stored into Resource class
	 * 
	 * @param line
	 *            The line read from configuration file
	 */
	private void buildNodes(String line) {
		// split the lines to tokens
		String[] nodeToken = line.split("\\s+");
		
		// create new nodes, add to list
		NodeRegistry.getInstance().register(
				new Node(Integer.parseInt(nodeToken[0]), nodeToken[1],
						nodeToken[2]));
	}
}
