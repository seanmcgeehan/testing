import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class InformationSpread implements IInformationSpread {
    
    /*
     * TODO: pass style checker
     */
    
    Graph graph = new GraphM();
    double numberOfVertices;
    int depth;
    ArrayList<Integer> vertList = new ArrayList<Integer>();

    @Override
    public int createFileWithRandomProbability(String filePath, String writePath) {
        int linesWritten = 0;
        try {            
            File writeFile = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(writeFile));
            FileWriter writer = new FileWriter(writePath);
            String linePointer;
            while ((linePointer = br.readLine()) != null) {
                int randomWeight = 1 + (int)(Math.random() * ((100 - 1) + 1));
                System.out.println(linePointer + " " + randomWeight);
                writer.write(linePointer + " " + randomWeight + "\n");
                linesWritten++;
            }
            br.close();
            writer.close();
            System.out.println(linesWritten);
            return linesWritten;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        
        return 0;
    }

    @Override
    public int loadGraphFromDataSetWithRandomProbs(String filePath) {
        // steps:
        // 1. open file
        // 2. retrieve data and add edge
        // 3. return number of nodes
                
        File file = new File(filePath);
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String firstLine = br.readLine();
            int index = firstLine.indexOf(' ');
            String words = firstLine.substring(0, index);
            numberOfVertices = Integer.parseInt(words);
            graph.init((int) (numberOfVertices + 1));
            String linePointer;
            
            while ((linePointer = br.readLine()) != null) {
                int littleIndex = linePointer.indexOf(' ');
                int bigIndex = linePointer.lastIndexOf(' ');
                String vWord = linePointer.substring(0, littleIndex);
                int v = Integer.parseInt(vWord);
                String wWord = linePointer.substring(littleIndex + 1, bigIndex);
                int w = Integer.parseInt(wWord);
                String weight = linePointer.substring(bigIndex + 1);
                int wgt = Integer.parseInt(weight);
                
                if (v > 0 && w > 0) {
                    graph.addEdge(v, w, wgt);
                    graph.addEdge(w, v, wgt);
                    if (!vertList.contains(v)) {
                        vertList.add(v);
                    }
                    if (!vertList.contains(w)) {
                        vertList.add(w);
                    }
                }

            }
            br.close();
            return (int) numberOfVertices;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    @Override
    public int[] getNeighbors(int id) {
        return (graph.neighbors(id));
    }

    @Override
    public int[] getInfectNeighbors(int id, double threshold) {
        
        int number = 0;
        
        for (int node : graph.neighbors(id)) {
            if (graph.weight(id, node) > (threshold * 100)) {
                number++;
            }
        }
        
        int[] temp = new int[number];
        int index = 0;
        
        for (int neighbor : graph.neighbors(id)) {
            if (graph.weight(id, neighbor) > (threshold * 100)) {
                temp[index] = neighbor;
                index++;
            }
        }
        
        System.out.println("neighbors infected: " + temp.length);     
        return temp;
    }

    @Override
    public double getWeight(int id1, int id2) {
        return (graph.weight(id1, id2));
    }

    @Override
    public Collection<Integer> path(int source, int destination, double threshold) {
        
        LinkedList<Integer> queue = new LinkedList<Integer>();
        LinkedList<Integer> shortestPath = new LinkedList<>();
        float[] dist = new float[(int) (numberOfVertices + 1)];
        int[] pred = new int[(int) (numberOfVertices + 1)];
        
        for (int i = 0; i < numberOfVertices + 1; i++) {
            dist[i] = INFINITY;//was infinity
            pred[i] = -1;
        }

        graph.setValue(source, VISITED);
        dist[source] = 1;
        queue.add(source);

        while (!queue.isEmpty()) {  
            
            int s = queue.remove();
            System.out.println("infected size starting at " + s + ": " + getNeighbors(s).length);
            
            for (int neighborNode : getInfectNeighbors(s, threshold)) {
                
                System.out.println(neighborNode + " " + s);
                
                if (graph.getValue(neighborNode) != VISITED) {
                    
                    graph.setValue(neighborNode, VISITED);
                    
                    if(dist[neighborNode] == INFINITY) {
                        dist[neighborNode] = (float) (dist[s]*1.0 * ( graph.weight(s, neighborNode)*1.0/100));
                        pred[neighborNode] = s;
                    }
                    else {
                        if(dist[neighborNode] < (dist[s]*1.0 * ( graph.weight(s, neighborNode)*1.0/100))) {
                            dist[neighborNode] = (float) (dist[s]*1.0 * ( graph.weight(s, neighborNode)*1.0/100));  
                            pred[neighborNode] = s;
                        }
                    }

                    queue.add(neighborNode);
                }
            }            
        }
        
        if(dist[destination]== INFINITY) {
            System.out.println("no path exists") ;
            return shortestPath;
        }
        int reverseIndexer = destination;
        shortestPath.add(reverseIndexer);
        while (pred[reverseIndexer] != -1) {
            shortestPath.add(pred[reverseIndexer]);
            reverseIndexer = pred[reverseIndexer];
        }
        // now need to reverse shortestPath
        LinkedList<Integer> shortestPathReversed = new LinkedList<>();
        for (int i : shortestPath) {
            shortestPathReversed.addFirst(i);  
        }
        System.out.println("path: " + shortestPathReversed);
        return shortestPathReversed;
    }

    @Override
    public double pathPercent(int source, int destination, double threshold) {
        double percent = 1;
        int prev = source;
        for (int x : path(source, destination, threshold)) {
            if(x != source) {
               percent = percent * getWeight(prev, x);
            }
            prev = x;
        }
        return percent;
    }

    @Override
    public int spreadLevels(int seed, double threshold, double probThreshold) {
        ArrayList<Integer> vertListSorted = new ArrayList<Integer>();
        vertListSorted = vertList;
        Collections.sort(vertList, Collections.reverseOrder());
        vertList = vertListSorted;
        
        // need to manually reset graph
        for (int element : vertList) {
            graph.setValue(element, null);
        }
        
        // check if seed is valid (is in the graph) -> -1   
        if (seed < 1 || seed > vertList.get(0)) {
            return -1;
        }        
        
        // check if the threshold is negative or greater than 1 -> -1
        if (threshold > 1 || threshold < 0) {
            return -1;
        }
        
        // check if threshold is 0 -> 0
        if (threshold == 0) {
            return 0;
        }
                
        double numNeeded = numberOfVertices * threshold;
        // calculate spread levels
//        System.out.format("num needed: " + numNeeded);
        
        LinkedList<Integer> currentLevelQueue = new LinkedList<Integer>(); 
                
        currentLevelQueue.add(seed);   
        graph.setValue(seed, VISITED);
        
        // the seed is infected
        double infectedTracker = 1;
        int level = 1;
                
        while (!currentLevelQueue.isEmpty()) {
            int levelSize = currentLevelQueue.size();
            while (levelSize-- > 0) {
                int temp = currentLevelQueue.poll();
                
                for (int neighbor : getInfectNeighbors(temp, probThreshold)) {
                    if (graph.getValue(neighbor) != VISITED) {
                        currentLevelQueue.add(neighbor);
                        graph.setValue(neighbor, VISITED);
                        infectedTracker++;
                    }
                    if (infectedTracker >= numNeeded) {
                        return level;
                    }
                }
                if (infectedTracker >= numNeeded) {
                    return level;
                }
            }
            level++;
        }
        return 0;
    }

    @Override
    public int degree(int n, double threshold) {
        // check if seed is valid (is in the graph) -> -1   
        ArrayList<Integer> vertListSorted = new ArrayList<Integer>();
        vertListSorted = vertList;
        Collections.sort(vertList, Collections.reverseOrder());
        
        if (n < 1 || n > vertList.get(0)) {
            return -1;
        }
        
        vertList = vertListSorted;
        
        // return degree of given node
        return getInfectNeighbors(n, threshold).length;
    }

    @Override
    public Collection<Integer> degreeNodes(int d, double threshold) {
        // steps:
        // 1. for every vertex in graph, if it's degrees == d, add to collection
        ArrayList<Integer> nodesWithDDegree = new ArrayList<Integer>();
        
        for (int i = 0; i < vertList.size(); i++) {
            if (degree(i, threshold) == d) {
                nodesWithDDegree.add(i);
            }
        }
        
        return nodesWithDDegree;
    }

    @Override
    public double clustCoeff(int n, double threshold) {
     // check if seed is valid (is in the graph) -> -1   
        ArrayList<Integer> vertListSorted = new ArrayList<Integer>();
        vertListSorted = vertList;
        Collections.sort(vertList, Collections.reverseOrder());
        
        if (n < 1 || n > vertList.get(0)) {
            return -1;
        }
        
        vertList = vertListSorted;
        
        if (degree(n, threshold) == 0 || degree(n, threshold) == 1) {
            return 0;
        }
                
        double edgeCounter = 0.0; // numerator
        
        for (int neighbor : getInfectNeighbors(n, threshold)) {
            for (int subNeighbor : getNeighbors(n)) {
                if (graph.hasEdge(neighbor, subNeighbor)) {
                    edgeCounter++;
                }
            }
        }
        
        double potentialEdges = degree(n, threshold) * (degree(n, threshold) - 1);
        
        // number of edges from node over total number of nodes
        return edgeCounter / potentialEdges;
    }

    @Override
    public Collection<Integer> clustCoeffNodes(double low, double high, double threshold) {
        Collection<Integer> clusteredNodes = new ArrayList<Integer>();      
        
        // for each vertex, check it's clustCoeff 
        for (int i = 0; i < vertList.size(); i++) {
            int clustCoeffInt = (int) (clustCoeff(i, threshold) * 100);
            int lowInt = (int) (low * 100);
            int highInt = (int) (high * 100);
                        
            if (clustCoeffInt >= lowInt && clustCoeffInt <= highInt) {
                clusteredNodes.add(i);
            }
        }
        return clusteredNodes;

    }

    @Override
    public Collection<Integer> highDegLowCCNodes(int lowBoundDegree, double upBoundCC, double threshold) {
        Collection<Integer> ccNodes = new ArrayList<Integer>();
        
        for (int i = 0; i < vertList.size(); i++) {
            if ((clustCoeff(i, threshold) <= upBoundCC) && degree(i, threshold) >= lowBoundDegree) {
                ccNodes.add(i);
            }
        }
        return ccNodes;

    }

    @Override
    public int spreadLevelsHighDegLowCC(int seed, double threshold, int lowBoundDegree, double upBoundCC, double probThreshold) {
        // check if seed is valid (is in the graph) -> -1   
        ArrayList<Integer> vertListSorted = new ArrayList<Integer>();
        vertListSorted = vertList;
        Collections.sort(vertList, Collections.reverseOrder());
        
        if (seed < 1 || seed > vertList.get(0)) {
            return -1;
        }
        
        vertList = vertListSorted;
        
        // check if the threshold is negative or greater than 1 -> -1
        if (threshold > 1 || threshold < 0) {
            return -1;
        }
        
        //return -1 if no node in the graph meets the clustering coefficient and degree requirements
        // check if no node in the graph has clust Coeff in given range -> -1
        if (clustCoeffNodes(0, upBoundCC, probThreshold).size() == 0) {
            for (int node : getNeighbors(seed)) {
                if (degree(node, probThreshold) > lowBoundDegree) {
                    break;
                }
            }
            return -1;
        }
        
        // check if we are going to remove the seed
        if (highDegLowCCNodes(lowBoundDegree, upBoundCC, probThreshold).contains(seed)) {
            return 0;
        }    
        
        // return 0 if we cannot reach the target threshold.
        
        Collection<Integer> ccNodes = new ArrayList<Integer>();
        ccNodes = highDegLowCCNodes(lowBoundDegree, upBoundCC, probThreshold);
        
        // remove nodes/degrees that have between range
        for (int node : ccNodes) {
            for (int neighbor : getNeighbors(node)) {
                graph.removeEdge(node, neighbor);
                graph.removeEdge(neighbor, node);
            }
        }
        return spreadLevels(seed, threshold, probThreshold);
    }

}