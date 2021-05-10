import org.junit.Test;

public class InformationSpreadTestCovid {
    
    // GOAL: See how Covid vaccinations impact the FB Data Set
    double usFullyImmune = .348 ;
    
    @Test
    public void testPath() {
        InformationSpread is = new InformationSpread();
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Path between 1 to 10 was length 3 pre-vaccinations. With vaccinations,"
                + " it is: \t " + is.path(1, 10, usFullyImmune).size());
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Path between 100 to 999 was length 4 pre-vaccinations. With vacations, "
                + "it is: \t" + is.path(100, 999, usFullyImmune).size());
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Path from 1 to 4000 was length 3 pre-vaccinations. With vaccinations, "
                + "it is: \t" + is.path(1, 4000, usFullyImmune).size());
        
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Path from 2500 to 6001 was len 7 pre-vaccinations. With vaccinations, "
                + "it is: \t " + is.path(2500, 6001, usFullyImmune).size());
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Path from 50 to 6001 was length 7 pre-vaccinations. With vaccinations, "
                + "it is: \t" + is.path(50, 6001, usFullyImmune).size());
    }

    
    @Test
    public void testSpreadLevels() {
        InformationSpread is = new InformationSpread();
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Spread level starting at 1, reaching 1% of nodes took 1 spread level"
                + " pre-vaccination. With vaccinations, it is: \t" + is.spreadLevels(1, .01,
                        usFullyImmune));
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Spread level starting at 100, reaching 50% of nodes took 3"
                + " spread levels"
                + " pre-vaccination. With vaccinations, it is: \t" + is.spreadLevels(100, .5,
                        usFullyImmune));
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Spread level starting at 1000, reaching 30% of nodes took 2"
                + " spread levels"
                + " pre-vaccination. With vaccinations, it is: \t" + is.spreadLevels(1000, .3,
                        usFullyImmune));
    }
    
    
    @Test
    public void testDegrees() {
        InformationSpread is = new InformationSpread();
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Node 1 had degree of 67, pre-vaccination. With vaccinations, "
                + "it is: \t" + is.degree(1, usFullyImmune));
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Node 50 had degree of 160, pre-vaccination. With vaccinations, "
                + "it is: \t" + is.degree(50, usFullyImmune));
        is.loadGraphFromDataSetWithRandomProbs("socfb-American75-Output.mtx");
        System.out.println("Node 5555 had degree of 246, pre-vaccination. With vaccinations, "
                + "it is: \t" + is.degree(5555, usFullyImmune));      
    }    
    
}
