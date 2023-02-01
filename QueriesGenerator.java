import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueriesGenerator {

    // List to store all generated sourceCode
    private List<String> queries;
    private List<String> procedures;
    private List<String> variables;
    private List<String> constants;
    private Map<Integer ,String> statements;

    public QueriesGenerator(Tester tester) {
        queries = tester.getQueries();
        procedures = tester.getProcedures();
        variables = tester.getVariables();
        constants = tester.getConstants();
        statements = tester.getStatements();
    }
    
    public void generateQueries() {
        List<String> types = new ArrayList<>(Arrays.asList("procedure", "variable", "constant", "assignment", "print", "read", "statement"));
        Map<String, List<String>> lists = new HashMap<>();
        lists.put("procedure", procedures);
        lists.put("variable", variables);
        lists.put("constant", constants);

        for (int i = 0; i < types.size(); i++) {
            StringBuilder sb = new StringBuilder();
            // 1 - Procedure
            String stmtType = types.get(i);
            sb.append(String.valueOf(i+1) + " - ");
            sb.append(stmtType.substring(0,1).toUpperCase() + stmtType.substring(1) + "\n");
            queries.add(sb.toString());
            sb.setLength(0);

            // procedure p;
            String str = stmtType;
            if (stmtType.equals("assignment")) {
                str = "assign";
            }
            else if (stmtType.equals("statement")) {
                str = "stmt";
            }
            sb.append(str + " " + str.substring(0, 1) + ";\n");
            queries.add(sb.toString());
            sb.setLength(0);

            // Select p
            sb.append("Select " + str.substring(0, 1) + "\n");
            queries.add(sb.toString());
            sb.setLength(0);

            // Expected result
            switch (stmtType) {
                case "procedure":
                case "variable":
                case "constant":
                    for (String string : lists.get(stmtType)) {
                        sb.append(string + ", "); 
                    }
                    break;
                
                case "assignment":
                    stmtType = "assign";
                case "print":
                case "read":
                    for (java.util.Map.Entry<Integer, String> entry : statements.entrySet()) {
                        if (entry.getValue() == stmtType) {
                            sb.append(entry.getKey() + ", "); 
                        }
                    }
                    break;
                case "statement":
                    for (Integer key : statements.keySet()) {
                        sb.append(key.toString() + ", ");
                    }
                    break;
            }
            if (sb.length() != 0) {
                sb.delete(sb.length() - 2, sb.length());
            }
            else {
                sb.append("none");
            }
            sb.append("\n");
            queries.add(sb.toString());
            sb.setLength(0);

            // Timeout
            queries.add("5000\n");
        }
    }
}