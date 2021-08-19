
package assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class GraphPoet {
	
	Map<String, ArrayList<Edge>> graph;


    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {

        /* Read in the File and place into graph here */
    	//input array to place lines
    	ArrayList<String> fileInput = new ArrayList<String>();
    	try {
    		Scanner reader = new Scanner(corpus);
    		while(reader.hasNextLine()) {
    			//get next line from corpus file
    			String in = reader.nextLine();
    			in = in.toLowerCase();
    			//add line to input array
    			fileInput.add(in);
    		}
    		reader.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	//create map to add new words to
    	this.graph = new HashMap<String, ArrayList<Edge>>();
    	
    	
    	//add words as vertices to graph
    	for(int i=0;i<fileInput.size();i++) {
    		String currentS = fileInput.get(i);
    		ArrayList<String> currentTokens = new ArrayList<String>();
    		StringTokenizer tokenizer = new StringTokenizer(currentS);
    		while(tokenizer.hasMoreTokens()) {
    			String myToken = tokenizer.nextToken();
    			//myToken.toLowerCase();
    			currentTokens.add(myToken);
    		}
    		for(int j=0;j<currentTokens.size();j++) {
    			if(j+1<currentTokens.size()) {
	    			if(graph.containsKey(currentTokens.get(j))) {
	    				//get arraylist of edges
	    				ArrayList<Edge> edgeList = graph.get(currentTokens.get(j));
	    				//check for edge between current token/word and next word
	    				boolean noEdge = true;
	    				for(int k=0;k<edgeList.size();k++) {
	    					if(edgeList.get(k).getName().equals(currentTokens.get(j+1))) {
	    						//increase edge weight for already existing edge
	    						int addEdgeCount = edgeList.get(k).getWeight();
	    						addEdgeCount++;
	    						edgeList.get(k).setWeight(addEdgeCount);
	    						noEdge = false;
	    					}
	    				}
	    				if(noEdge) {
	    					//add new edge
	    					Edge newEdge = new Edge(currentTokens.get(j+1), 1);
	    					graph.get(currentTokens.get(j)).add(newEdge);
	    				}
	    			}else {
	    				//add vertex to graph
	    				ArrayList<Edge> emptyEdgeList = new ArrayList<Edge>();
	    				Edge newEdge = new Edge(currentTokens.get(j+1), 1);
	    				emptyEdgeList.add(newEdge);
	    				graph.put(currentTokens.get(j), emptyEdgeList);
	    			}
    			}else {
    				if(i+1<fileInput.size()) {
    					StringTokenizer lastTok = new StringTokenizer(fileInput.get(i+1));
    					ArrayList<String> nextLineToks = new ArrayList<String>();
    					while(lastTok.hasMoreTokens()) {
    		    			String lToken = lastTok.nextToken();
    		    			lToken = lToken.toLowerCase();
    		    			nextLineToks.add(lToken);
    		    		}
    					if(graph.containsKey(currentTokens.get(j))){
    						//get arraylist of edges
    	    				ArrayList<Edge> edgeList = graph.get(currentTokens.get(j));
    	    				//check for edge between current token/word and next word
    	    				boolean noEdge = true;
    	    				for(int k=0;k<edgeList.size();k++) {
    	    					if(edgeList.get(k).getName().equals(nextLineToks.get(0))) {
    	    						//increase edge weight for already existing edge
    	    						int addEdgeCount = edgeList.get(k).getWeight();
    	    						addEdgeCount++;
    	    						edgeList.get(k).setWeight(addEdgeCount);
    	    						noEdge = false;
    	    					}
    	    				}
    	    				if(noEdge) {
    	    					//add new edge
    	    					Edge newEdge = new Edge(nextLineToks.get(0), 1);
    	    					graph.get(currentTokens.get(j)).add(newEdge);
    	    				}
    					}else {
    						ArrayList<Edge> emptyEdgeList = new ArrayList<Edge>();
    	    				Edge newEdge = new Edge(nextLineToks.get(0), 1);
    	    				emptyEdgeList.add(newEdge);
    	    				graph.put(currentTokens.get(j), emptyEdgeList);
    					}
    				}else {
	    				if(!graph.containsKey(currentTokens.get(j))) {
	    					ArrayList<Edge> emptyEdgeList = new ArrayList<Edge>();
		    				graph.put(currentTokens.get(j), emptyEdgeList);
	    				}
    				}
    			}
    		}
    	}
    	
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {

        /* Read in input and use graph to complete poem */
    	Scanner reader = new Scanner(input);
    	//create list with lines of the poem
    	ArrayList<String> poemInput = new ArrayList<String>();
    	while(reader.hasNextLine()) {
    		//get next line as input
    		String in = reader.nextLine();
    		in = in.toLowerCase();
    		//add to the input list
    		poemInput.add(in);
    	}
    	String poem = "";
    	for(int i=0;i<poemInput.size();i++) {
    		ArrayList<String> tokens = new ArrayList<String>();
    		String subPoem = "";
    		StringTokenizer tokenizer = new StringTokenizer(poemInput.get(i));
    		while(tokenizer.hasMoreTokens()) {
    			String tok = tokenizer.nextToken();
    			tokens.add(tok);
    		}
    		for(int j=0;j<tokens.size();j++) {
    			subPoem = subPoem + " " + tokens.get(j);
    			if(j+1<tokens.size()) {
    				boolean foundWord = false;
    				String addedWord = "";
    				if(graph.containsKey(tokens.get(j))) {
	    				ArrayList<Edge> edges = graph.get(tokens.get(j));
	    				int weight = 0;
	    				for(int k=0;k<edges.size();k++) {
	    					Edge currentEdge = edges.get(k);
	    					String word = currentEdge.getName();
	    					ArrayList<Edge> wordEdges = graph.get(word);
	    					for(int w=0;w<wordEdges.size();w++) {
	    						if(wordEdges.get(w).getName().equals(tokens.get(j+1))) {
	    							int currentWeight = 0;
	    							for(int z=0;z<edges.size();z++) {
	    								if(edges.get(k).getName().equals(word)){
	    									currentWeight = edges.get(k).getWeight();
	    								}
	    							}
	    							if(currentWeight>weight) {
	    								addedWord = word;
	    								weight = currentWeight;
	    								foundWord = true;
	    							}
	    						}
	    					}
	    				}
    				}
    				if(foundWord) {
    					subPoem = subPoem + " " + addedWord;
    				}
    			}
    		}
    		poem = poem + " " + subPoem + "\n";
    	}
        return poem;
    }

}
