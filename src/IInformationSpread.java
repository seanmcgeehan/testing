import java.util.Collection;
/**
 */
public interface IInformationSpread {

    public static final Object VISITED  = -5;

    public static final int    INFINITY = Integer.MAX_VALUE;

    /**
     * Create a file with random probabilities.
     * @param filePath the path of the data, path of the new file
     * @return the number of lines written to the new file
     */
    //test that file was created
    int createFileWithRandomProbability(String filePath, String writePath);

    /**
     * Create a graph representation of the dataset.
     * The first line of the file
     * contains the number of nodes add 1 to the number of nodes in the graph
     * since there is no node with id 0
     * weight will have the be ints
     *
     * @param filePath the path of the data
     * @return the number of entries (nodes) in the dataset (graph)
     */
    int loadGraphFromDataSetWithRandomProbs(String filePath);

    /**
     * Return the neighbors ids of a specific node
     *
     * @param id the id of the page
     * @return the array of neighbor(s) by random Weight
     */
    int[] getNeighbors(int id);

    /**
     * Return the infected neighbors ids of a specific node
     *
     * @param id the id of the page, threshold for succesful infection
     * @return the array of infected neighbor(s)
     */
    int[] getInfectNeighbors(int id, double threshold);

    /**
     * Return the weight of a specific node
     *
     * @param id of the node
     * @return the weight
     */
    double getWeight(int id1, int id2);

    /** will be using dijsktra's
     * return the shorthest path between two nodes
     * include the source and destination nodes in your collection
     * @param source      - the id of the origin node
     * @param destination - the id of the destination node
     * @param threshold   -  percent of nodes infected
     * @return collection of nodes to follow to go from source to destination
     */
    Collection<Integer> path(int source, int destination, double threshold);

    /**
     * return the probability of making it thru the entire path
     * @param source      - the id of the origin node
     * @param destination - the id of the destination node
     * @param threshold   -  percent of nodes infected
     * @return double - the probability
     */
    double pathPercent(int source, int destination, double threshold);

    /**
     * Given a specific node id (seed) this method will return the number of
     * "spreadLevels" necessary to reach a percentage (threshold) of the nodes
     * in the graph
     *
     * @param seed      - the id of the seed page
     * @param threshold - the percentage of nodes to reach
     * @param probThreshold   -  percent of nodes infected
     * @return the number of spread Levels necessary to reach threshold percent
     *         nodes in the graph or -1 if the seed is not in the graph
     */
    // pick a random number, for any node's weight, if it's lower, let it be

    int spreadLevels(int seed, double threshold, double probThreshold);

    /**
     * @param n the node, threshold for infecting
     * @return the degree of the node
     */
    int degree(int n, double threshold);

    /**
     * @param d the degree, threshold for infecting
     * @return all the node with degree d
     */
    Collection<Integer> degreeNodes(int d, double threshold);

    /**
    * nodes with degree 0 or 1 have a cc of 0
    * @param n the node,  threshold for infecting
    * @return the  clustering coefficient of n
    */
    double clustCoeff(int n, double threshold);

   /**
    * precision: 0.01 (use when comparing CC values)
    * @param low - the lower bound (inclusive) of the cc range
    * @param high - the upper bound (inclusive) of the cc range
    * @param  threshold - infecting
    * @return a collection of nodes with a clustering coefficient
    * within [low, high]
    */
   Collection<Integer> clustCoeffNodes(double low, double high, double threshold);

   /**
    * precision: 0.01
    * @param lowBoundDegree - the lower bound (inclusive) of the degree
    * @param upBoundCC - the upper bound (inclusive) of the cc
    * @return a collection of nodes with degree >= lowBoundDegree and
    *  clustering coefficient <= upBoundCC
    */
   Collection<Integer> highDegLowCCNodes(int lowBoundDegree, double upBoundCC, double threshold);

   /**
    * Given a specific node id (seed) this method will return the number of
    * "spreadLevels" necessary to reach a percentage (threshold) of the nodes
    * in the graph when all the nodes with a clustering coefficient below a
    * given value and a degree above a given value are removed.
    *
    * @param seed      - the id of the seed page
    * @param threshold - the percentage of nodes to reach
    * @param lowBoundDegree - the lower bound (inclusive) of the degree
    * @param upBoundCC - the upper bound (inclusive) of the cc
    * @return the number of spread Levels necessary to reach threshold percent
    *         nodes in the graph
    */
   int spreadLevelsHighDegLowCC(int seed, double threshold, int lowBoundDegree, double upBoundCC, double probThreshold);
    
}
