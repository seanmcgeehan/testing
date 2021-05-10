import static org.junit.Assert.*;
import java.io.File;
import java.util.Collection;
import org.junit.Test;

public class InformationSpreadTest {
        
    InformationSpread is = new InformationSpread();
    
    @Test
  public void testCreateFileWithRandomProbability() {
        File file = new File("babytestoutput2.txt");
        int linesRead = is.createFileWithRandomProbability(
              "babytest.txt", 
              "babytestoutput2.txt");
        assertTrue(file.exists());
        assertEquals(linesRead, 12);
    }
        
    @Test
    public void testLoadGraph() {
        int numberOfNodes = is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        assertEquals(numberOfNodes, 10);
    }
    
    @Test
    public void testGetNeighbors() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int[] numNeighbors = is.getNeighbors(2);
        assertEquals(numNeighbors.length, 5);
    }
    
    @Test
    public void testGetInfectNeighbors() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int[] numInfected = is.getInfectNeighbors(2, .3);
        assertEquals(numInfected.length, 3);
        numInfected = is.getInfectNeighbors(2, 0);
        assertEquals(numInfected.length, 5);
    }
    
    @Test
    public void testGetWeight() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int weight = is.getWeight(1, 2);
        assertEquals(weight, 13);
    }
    
    @Test
    public void testPath() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        Collection<Integer> path = is.path(1, 4, 0.1);
        assertEquals(path.size(), 3);       
    }
    
    @Test
    public void testPathInvalid() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        Collection<Integer> path = is.path(1, 4, 2);
        assertNull(path);       
    }
    
    @Test
    public void testPathPercentSimple() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        double percent = is.pathPercent(1, 2, 0);
        assertEquals(percent, is.getWeight(1, 2), .001);
    }
    
    @Test
    public void testPathPercentComplex() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        double percent = is.pathPercent(1, 3, 0);
        assertEquals(percent, is.getWeight(1,2) * is.getWeight(2,3), .001);
    }
    
    @Test
    public void testPathPercentInvalid() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        double percent = is.pathPercent(1, 3, -2);
        assertEquals(percent, 0, .001);
    }
    
    @Test
    public void testSpreadLevels() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int levels = is.spreadLevels(1, .3, .12);
        assertEquals(levels, 2);
    }
    
    @Test
    public void testSpreadLevelsInvalid() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int levels = is.spreadLevels(1, .3, 2000);
        assertEquals(levels, 0);
        int levels2 = is.spreadLevels(-3, .3, .5);
        assertEquals(levels2, -1);
        int levels3 = is.spreadLevels(1, 4, .5);
        assertEquals(levels3, -1);
        int levels4 = is.spreadLevels(1, 0, .5);
        assertEquals(levels4, 0);

    }

    @Test
    public void testDegree() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int neighborLen = is.degree(1, .1);
        assertEquals(neighborLen, 1);
    }

    @Test
    public void testDegreeNodes() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int neighborLen = is.degreeNodes(1, .1).size();
        assertEquals(neighborLen, 4);
    }

    @Test
    public void testClustCoeff() {       
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        double edgePer = is.clustCoeff(2, .1);
        assertEquals(edgePer, 0.1, .001);      
    }
    
    @Test
    public void testClustCoeffNodes() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int neighborLen = is.clustCoeffNodes(0,.5, .3).size();
        assertEquals(neighborLen, 8);
    }
    
    @Test
    public void testHighDegLowCCNodes() {
        is.loadGraphFromDataSetWithRandomProbs(
                "babytestoutput.txt");
        int neighborLen = is.highDegLowCCNodes(1,.5, .5).size();
        assertEquals(neighborLen, 5);
    }
    
    @Test
    public void testSpreadLevelsHighDegLowCC() {
        is.loadGraphFromDataSetWithRandomProbs(
                "socfb-American75-Output.mtx");
        int levels = is.spreadLevelsHighDegLowCC(1, .1, 1, .2, .3);
        assertEquals(levels, 2);
    }

    
    // this test takes several minutes to run but confirms given test values from
    // the last HW
    
    @Test
    public void testSpreadLevelsHighDegLowCCFb() {
        InformationSpread is = new InformationSpread();
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        assertEquals(is.spreadLevelsHighDegLowCC(1, .25, 90, .25, 0), 3);
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        assertEquals(is.spreadLevelsHighDegLowCC(10, .5, 90, .25, 0), 0);
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        assertEquals(is.spreadLevelsHighDegLowCC(10, .5, 90, .75, 0), 0);
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        assertEquals(is.spreadLevelsHighDegLowCC(100, .3, 100, .25, 0), 4);
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        assertEquals(is.spreadLevelsHighDegLowCC(1000, .5, 100, .25, 0), 3);
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        assertEquals(is.spreadLevelsHighDegLowCC(1000, .3, 75, .25, 0), 3);
    }
    
}
    
    
    
