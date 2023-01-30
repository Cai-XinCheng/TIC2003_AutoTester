import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * An auto tester for TIC2003 project (Iteration 1)
 * 
 * 1. Generate SIMPLE source code
 * 2. Generate queries
 * 3. Execute autoTester.exe to test generated testcase
 * 
 */

public class Tester {

    // Number of testcases to be generated and tested
    private static final int caseCount = 1;

    // Path of generated sourceCode
    private static final String sourceCodePath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\Tester\\sourceCode";
    // Path of generated queries
    private static final String queriesPath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\Tester\\queries";
    // Path of generated output files
    private static final String outputPath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\Tester\\output";
    // Path of AutoTester.exe
    private static final String testerPath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\SPA\\Code\\Debug\\AutoTester.exe";

    // List to store all generated sourceCode
    private List<String> sourceCode;
    // List to store all generated queries
    private List<String> queries;
    private Random random = new Random();
    
    public Tester() {
        sourceCode = new ArrayList<>();
        queries = new ArrayList<>();
    }

    public void generateSourceCode() {
        
    }

    public void generateQueries() {

    }

    public void test() {

    }

    public static void main(String[] args) {

        for (int i = 0; i < caseCount; i++) {
            // Init
            Tester tester = new Tester();
            // Generate source code
            tester.generateSourceCode();
            // Generate queries
            tester.generateQueries();
            // Execute and test
            tester.test();
        }
    }
}
