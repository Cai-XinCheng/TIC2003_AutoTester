import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class sourceCodeGenerator {

    
    private Random random = new Random();
    // List to store all generated sourceCode
    private List<String> sourceCode;
    private List<String> procedures;
    private List<String> variables;
    private List<String> constants;
    private Map<Integer ,String> statements;

    public sourceCodeGenerator(Tester tester) {
        sourceCode = tester.getSourceCode();
        procedures = tester.getProcedures();
        variables = tester.getVariables();
        constants = tester.getConstants();
        statements = tester.getStatements();
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
        Integer constant;
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
                constant = getConstant();
                sourceCode.add(constant.toString());
                constants.add(constant.toString());
            }
            else {
                sourceCode.add(variableName2);
            }
        }
        else {
            constant = getConstant();
            sourceCode.add(constant.toString());
            constants.add(constant.toString());
        }
        sourceCode.add(";");
    }
    
}
