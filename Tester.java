
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String path = "C:\\Users\\Smile\\Documents\\XinCheng\\TIC2003\\Tester";
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
    private List<String> output;
    private List<String> procedures;
    private List<String> variables;
    private List<String> constants;
    private Map<Integer ,String> statements;

    private Map<List<String>, String> fileNames;
    
    public Tester() {
        sourceCode = new ArrayList<>();
        queries = new ArrayList<>();
        queries = new ArrayList<>();
        procedures = new ArrayList<>();
        variables = new ArrayList<>();
        constants = new ArrayList<>();
        statements = new HashMap<>();
        output = new ArrayList<>();
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

    public void writeFile(List<String> list, int index) {
        fileNames = new HashMap<>();
        fileNames.put(sourceCode, "/sourceCode");
        fileNames.put(queries, "/queries");
        
        try {
            FileWriter fw = new FileWriter(path + fileNames.get(list) + fileNames.get(list) + index + ".txt");
            StringBuilder sb = print(list);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearFolder() {
        for (File file : new File(path).listFiles()) {
            if (file.isDirectory()) {
                for (File subFile : new File(file.getPath()).listFiles()) {
                    if (subFile.getName().endsWith(".txt")) {
                        subFile.delete();
                    }
                }
            }
        }
    }

    public void execute(int index) {
        try {
            Process ps = new ProcessBuilder(testerPath, path+"/sourceCode/sourceCode"+index+".txt", path+"/queries/queries"+index+".txt", 
                                            path + "/output/output" + index + ".xml").start();
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line = br.readLine();
            while(line != null) {
                output.add(line + "\n");
                line = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void evaluate(int index) {
        int queryNo = 0;
        List<String> yourAnswer = new ArrayList<>();
        List<String> correctAnswer = new ArrayList<>();
        List<String> query = new ArrayList<>();
        for (String string : output) {
            if (string.matches("\\d\\s-\\s\\w+\\n")) { // 1 - Procedure
                queryNo = Integer.valueOf(string.substring(0, 1));
                query.clear();
            }
            else if (string.matches("Your answer:.*\\n")) {
                yourAnswer.addAll(Arrays.asList(string.substring(12, string.length()).split(" ")));
            }
            else if (string.matches("Correct answer:.*\\n")) {
                correctAnswer.addAll(Arrays.asList(string.substring(15, string.length()).split(" ")));
            }
            query.add(string);
            // Compare yourAnswer and correctAnswer
            if (!yourAnswer.isEmpty() && !correctAnswer.isEmpty()) {
                if (yourAnswer.containsAll(correctAnswer) && correctAnswer.containsAll(yourAnswer)) {
                    System.out.println("Testcase " + index + " - Query " + queryNo + " passed!");
                }
                else {
                    System.out.println("!!!Testcase "+ index + " - Query " + queryNo + " failed!!!");
                    System.out.println("-----Output of query " + index +":\n");
                    System.out.println(query);
                    System.out.println("-----End of query-----");
                }
                yourAnswer.clear();
                correctAnswer.clear();
            }
        }
        // System.out.println("---" + output.get(13) + "---");
        // System.out.println(output.get(13).matches("Your answer:.*\\n"));
    }

    

    public StringBuilder print(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string);
        }
        return sb;
    }

    public static void main(String[] args) {
        // Clear folders
        clearFolder();

        for (int i = 0; i < CASECOUNT; i++) {
            // Init
            Tester tester = new Tester();
            SourceCodeGenerator scGenerator = new SourceCodeGenerator(tester);
            QueriesGenerator qGenerator = new QueriesGenerator(tester);
            // Generate source code
            scGenerator.generateSourceCode();
            // Write source code into file
            tester.writeFile(tester.sourceCode, i+1);
            // Generate queries
            qGenerator.generateQueries();
            // Write queries into file
            tester.writeFile(tester.queries, i+1);
            // Execute and evaluate
            tester.execute(i+1);
            tester.evaluate(i+1);

            // Debug
            //System.out.println(tester.print(tester.sourceCode).toString());
            //System.out.println(tester.print(tester.queries).toString());
            //System.out.println(tester.print(tester.output).toString());
        }
    }
}
