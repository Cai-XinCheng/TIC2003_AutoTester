import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QueriesGenerator {

    private Random random = new Random();
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

        for (int i = 0; i < types.size(); i++) {
            StringBuilder sb = new StringBuilder(i + " - ");
            sb.append(types.get(i).substring(0,1).toUpperCase() + types.get(1).substring(1));
            queries.add(sb.toString());

            System.out.println(queries);
        }
    }
}