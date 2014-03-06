package com.mps.dsp.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mps.dsp.core.Node;
import com.mps.dsp.core.NodeRegistry;
import com.mps.dsp.util.Logger;

public class ConfigFileReader {
	 
	private final String TAG = ConfigFileReader.class.getSimpleName();

	/**
	 * This method parse the configuration file and transform content of
	 * configuration file into set of Nodes. These compiled Nodes are stored
	 * into the the Resource class.
	 */
	public void parseConfigFile() {
		//Logger.d(TAG, "parseConfigFile()");

		String workingDir = System.getProperty("user.dir");
		String packagePath = "/src/com/mps/dsp/config/";

		//System.out.println(workingDir);

		File file = new File(workingDir + packagePath + "/nodelist.txt");

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

		//Logger.d(TAG, "Total Nodes in System : " + NodeRegistry.getInstance().getNodes().size());
	}

	/**
	 * This method build Nodes for converting each lines String into tokens.
	 * Each token represent Node property which is stored into Resource class
	 * 
	 * @param line
	 *            The line read from configuration file
	 * @param nodeList
	 *            The List<Node> data structure as a holder to save Nodes.
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
