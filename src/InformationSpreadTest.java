import static org.junit.Assert.*;

import org.junit.Test;

public class InformationSpreadTest {
    
    @Test
    public void testCreateFileWithRandomProbability() {
        InformationSpread is = new InformationSpread();
        is.createFileWithRandomProbability("/Users/Jess/Desktop/HW7-Disease/babytest.txt", "/Users/Jess/Desktop/HW7-Disease/babytestoutput.txt");
    }

}
