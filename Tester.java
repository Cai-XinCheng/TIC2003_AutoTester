import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final int CASECOUNT = 5;

    // Path of generated sourceCode
    private static final String sourceCodePath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\Tester\\sourceCode";
    // Path of generated queries
    private static final String queriesPath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\Tester\\queries";
    // Path of generated output files
    private static final String outputPath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\Tester\\output";
    // Path of AutoTester.exe
    private static final String testerPath = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\SPA\\Code\\Debug\\AutoTester.exe";

    
    private Random random = new Random();
    // List to store all generated sourceCode
    private List<String> sourceCode;
    // List to store all generated queries
    private List<String> queries;
    private List<String> procedures;
    private List<String> variables;
    private List<String> constants;
    private Map<Integer ,String> statements;
    
    public Tester() {
        sourceCode = new ArrayList<>();
        queries = new ArrayList<>();
        queries = new ArrayList<>();
        procedures = new ArrayList<>();
        variables = new ArrayList<>();
        constants = new ArrayList<>();
        statements = new HashMap<>();
    }

    public static String getSourcecodepath() {
        return sourceCodePath;
    }
    public static String getQueriespath() {
        return queriesPath;
    }
    public List<String> getSourceCode() {
        return sourceCode;
    }
    public List<String> getQueries() {
        return queries;
    }
    public List<String> getProcedures() {
        return procedures;
    }
    public List<String> getVariables() {
        return variables;
    }
    public List<String> getConstants() {
        return constants;
    }
    public Map<Integer, String> getStatements() {
        return statements;
    }

    public void writeSourceFile(int index) {
        try {
            FileWriter fw = new FileWriter(sourceCodePath + "/sourceCode" + index + ".txt");
            StringBuilder sb = printSouceCode();
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateQueries() {

    }

    public static void clearFolder() {
        for (File file : new File(sourceCodePath).listFiles()) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    public void test() {

    }

    

    public StringBuilder printSouceCode() {
        StringBuilder sb = new StringBuilder();
        for (String string : sourceCode) {
            if (string.equals("procedure")) {
                sb.append(string);
            }
            else if (string.equals("{") || string.equals(";")) {
                sb.append(string);
                sb.append("\n    ");
            }
            else if (string.equals("}")) {
                sb.delete(sb.length()-4, sb.length());
                sb.append(string);
            }
            else {
                sb.append(" ");
                sb.append(string);
            }
        }
        return sb;
    }

    public static void main(String[] args) {
        // Clear folders
        clearFolder();

        for (int i = 0; i < CASECOUNT; i++) {
            // Init
            Tester tester = new Tester();
            sourceCodeGenerator scGenerator = new sourceCodeGenerator(tester);
            // Generate source code
            scGenerator.generateSourceCode();
            // Write source code into file
            tester.writeSourceFile(i+1);
            // Generate queries
            tester.generateQueries();
            // Execute and test
            tester.test();

            // Debug
            //System.out.println(tester.printSouceCode().toString());
        }
    }
}
