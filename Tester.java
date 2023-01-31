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
    private static final int CASECOUNT = 1;

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

    public void generateSourceCode() {
        // Procedure
        String procedureName = getName();
        sourceCode.add("procedure");
        sourceCode.add(procedureName);
        sourceCode.add("{");
        procedures.add(procedureName);

        // Statements
        // Random number of stataments
        int noOfStatment = 10;
        // Statement types
        List<String> stmtTypes = new ArrayList<>(Arrays.asList("read", "print", "assign"));
        for (int i = 0; i < noOfStatment; i++) {
            String stmtType = stmtTypes.get(random.nextInt(stmtTypes.size()));
            if (stmtType.equals("read")) {
                sourceCode.add("read");
                String variableName = getName();
                sourceCode.add(variableName);
                sourceCode.add(";");
                variables.add(variableName);
            }
            else if (stmtType.equals("print")) {
                if (variables.isEmpty()) {
                    i--;
                    break;
                }
                sourceCode.add("print");
                sourceCode.add(variables.get(random.nextInt(variables.size())));
                sourceCode.add(";");
            }
            else if (stmtType.equals("assign")) {
                getAssignStatement();
            }
            statements.put(i+1, stmtType);
        }
        sourceCode.add("}");
    }

    public void generateQueries() {

    }

    public void test() {

    }

    // A function to generate randomized procedure/variable name
    // Name format: [a-zA-Z][a-zA-Z0-9]*
    public String getName() {
        int MAX_LENGTH = 50;
        String alphbets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String characters = alphbets + digits;

        StringBuilder sb = new StringBuilder();
        sb.append(alphbets.charAt(random.nextInt(alphbets.length())));
        for (int i = 0; i < random.nextInt(MAX_LENGTH); i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    // A function to generate random integer
    public Integer getConstant() {
        return random.nextInt();
    }

    public void getAssignStatement() {
        // LHS = RHS
        // LHS: true - declared variable, false - new varible
        // RHS: true - declared variable, false - constant
        
        boolean lhs = random.nextBoolean();
        boolean rhs = random.nextBoolean();
        String variableName1;
        String variableName2;
        if (lhs && !variables.isEmpty()) {
            variableName1 = variables.get(random.nextInt(variables.size()));
            sourceCode.add(variableName1);
        }
        else {
            variableName1 = getName();
            sourceCode.add(variableName1);
        }
        sourceCode.add("=");
        if (rhs && !variables.isEmpty()) {
            variableName2 = variables.get(random.nextInt(variables.size()));
            if (variableName1.equals(variableName2)) {
                sourceCode.add(getConstant().toString());
            }
            else {
                sourceCode.add(variableName2);
            }
        }
        else {
            sourceCode.add(getConstant().toString());
        }
        sourceCode.add(";");
    }

    public void printSouceCode() {
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
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {

        for (int i = 0; i < CASECOUNT; i++) {
            // Init
            Tester tester = new Tester();
            // Generate source code
            tester.generateSourceCode();
            // Generate queries
            tester.generateQueries();
            // Execute and test
            tester.test();

            tester.printSouceCode();
        }
    }
}
