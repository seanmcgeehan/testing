import static org.junit.Assert.*;

import org.junit.Test;

public class InformationSpreadTest {
    
//    @Test
//    public void testCreateFileWithRandomProbability() {
//        InformationSpread is = new InformationSpread();
//        is.createFileWithRandomProbability("/Users/Jess/Desktop/HW7-Disease/babytest.txt", "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
//    }
    
    InformationSpread is = new InformationSpread();

    @Test
    public void testCreateFileWithRandomProbability() {
        is.createFileWithRandomProbability("/Users/Jess/Desktop/HW7-Disease/babytest.txt", "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
    }
    
    @Test
    public void testGetInfectNeighbors() {
        is.loadGraphFromDataSetWithRandomProbs("/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        is.getInfectNeighbors(2, .5);
    }
    
    @Test
    public void testPath() {
        is.loadGraphFromDataSetWithRandomProbs("/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
        is.path(1, 9, 1);
    }
    
    
    
}
