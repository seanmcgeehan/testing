import static org.junit.Assert.*;

import java.io.File;
import java.util.Collection;

import org.junit.Test;

public class InformationSpreadTest {
        
    InformationSpread is = new InformationSpread();
//
//    @Test
//    public void testCreateFileWithRandomProbability() {
//        File file = new File("/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
//        int linesRead = is.createFileWithRandomProbability(
//                "/Users/Jess/Desktop/HW7-Disease/babytest.txt", 
//                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
//        assertTrue(file.exists());
//        assertEquals(linesRead, 12);
//    }
    
    @Test
    public void testLoadGraph() {
        int numberOfNodes = is.loadGraphFromDataSetWithRandomProbs(
                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        assertEquals(numberOfNodes, 10);
    }
    
    @Test
    public void testGetNeighbors() {
        is.loadGraphFromDataSetWithRandomProbs(
                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        int[] numNeighbors = is.getNeighbors(2);
        assertEquals(numNeighbors.length, 5);
    }
    
    @Test
    public void testGetInfectNeighbors() {
        is.loadGraphFromDataSetWithRandomProbs(
                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        int[] numInfected = is.getInfectNeighbors(2, .5);
        assertEquals(numInfected.length, 2);
    }
    
    @Test
    public void testGetWeight() {
        is.loadGraphFromDataSetWithRandomProbs(
                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        int weight = is.getWeight(1, 2);
        assertEquals(weight, 66);
    }
    
    @Test
    public void testPath() {
        is.loadGraphFromDataSetWithRandomProbs(
                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        Collection<Integer> path = is.path(1, 5, 0.5);
        assertEquals(path.size(), 3);       
    }
    
    @Test
    public void testPathPercentSimple() {
        is.loadGraphFromDataSetWithRandomProbs(
                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        double percent = is.pathPercent(1, 2, 0);
        assertEquals(percent, is.getWeight(1, 2), .001);
    }
    
    @Test
    public void testPathPercentComplex() {
        is.loadGraphFromDataSetWithRandomProbs(
                "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        double percent = is.pathPercent(1, 3, 0);
        assertEquals(percent, is.getWeight(1,2) * is.getWeight(2,3), .001);
    }
    
    @Test
    public void testSpreadLevels() {
        
    }

    @Test
    public void testDegree() {
        
    }

    @Test
    public void testDegreeNodes() {
        
    }

    @Test
    public void testClustCoeff() {
        
    }
    
    @Test
    public void testClustCoeffNodes() {
        
    }
    
    @Test
    public void testHighDegLowCCNodes() {
        
    }
    
    @Test
    public void testSpreadLevelsHighDegLowCC() {
        
    }
    
}
    
    
    
