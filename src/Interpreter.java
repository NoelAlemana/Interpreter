import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
public class Interpreter {
    private HashMap<String, Object> variables;
    private Scanner scanner;

    // Define reserved words as constants
    private static final String BEGIN_KEYWORD = "BEGIN CODE";
    private static final String END_KEYWORD = "END CODE";
    private static final String NEXT_LINE_SYMBOL = "$";
    private static final String INT_KEYWORD = "INT";
    private static final String DISPLAY_KEYWORD = "DISPLAY:";
    private static final String CHAR_KEYWORD = "CHAR";
    private static final String BOOL_KEYWORD = "BOOL";
    private static final String FLOAT_KEYWORD = "FLOAT";
    private static final String SCAN_KEYWORD = "SCAN:";
    private static final String IF_KEYWORD = "IF";
    private static final String ENDIF_KEYWORD = "END IF";
    private static final String BEGINWHILE_KEYWORD = "BEGIN WHILE";
    private static final String ENDWHILE_KEYWORD = "END WHILE";
    private static final String COMMENT_KEYWORD = "#";

    private static final String ARITHMETIC_PARENTHESIS_OPEN = "(";
    private static final String ARITHMETIC_PARENTHESIS_CLOSE = ")";
    private static final String ARITHMETIC_MULTIPLICATION = "*";
    private static final String ARITHMETIC_DIVISION = "/";
    private static final String ARITHMETIC_MODULO = "%";
    private static final String ARITHMETIC_ADDITION = "+";
    private static final String ARITHMETIC_SUBTRACTION = "-";
    private static final String ARITHMETIC_GREATER_THAN = ">";
    private static final String ARITHMETIC_LESS_THAN = "<";
    private static final String ARITHMETIC_GREATERTHAN_EQUAL = ">=";
    private static final String ARITHMETIC_LESSTHAN_EQUAL = "<=";
    private static final String ARITHMETIC_EQUAL = "==";
    private static final String ARITHMETIC_NOTEQUAL = "<>";


    public Interpreter() {
        variables = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    //Helper Methods
    private int[] countCommasAndEquals(String line) {
        int commaCount = 0;
        int equalsCount = 0;

        // Count commas and equals signs
        for (char c : line.toCharArray()) {
            if (c == ',') {
                commaCount++;
            } else if (c == '=') {
                equalsCount++;
            }
        }

        // Return the counts as an array
        return new int[]{commaCount, equalsCount};
    }
    // End of Helper Methods

    private void declareVariable(String variableName, Object value) {
        if (variables.containsKey(variableName)) {
            throw new IllegalArgumentException("Variable " + variableName + " already exists");
        }
        variables.put(variableName, value);
    }
    public void setVariableValue(String variableName, Object value) {
        if (!variables.containsKey(variableName)) {
            throw new IllegalArgumentException("Variable " + variableName + " does not exist");
        }
        variables.put(variableName, value);
    }

    private Object getVariableValue(String variableName) {
        if (variables.containsKey(variableName)) {
            return variables.get(variableName);
        } else {
            System.err.println("Error: Variable " + variableName + " not found");
            return 0;
        }
    }

    private void displayVariables(String[] variableNames) {

        for (String variableName : variableNames) {
            String nospace = variableName.replaceAll("\\s", ""); // Remove all spaces
            String trimmed = variableName.trim();
            if (variables.containsKey(nospace)) {
                System.out.print(getVariableValue(nospace));
            }else if(nospace.equals("$")){
                System.out.println();
            }else if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
                System.out.print(trimmed.substring(1, trimmed.length() - 1));
            }else {
                System.err.println("Error: Variable " + variableName + " not found");
            }
        }
    }

    public void interpret(String sourceCode) {
        String[] lines = sourceCode.split("\n");

        if(!lines[0].startsWith(BEGIN_KEYWORD))
            throw new IllegalArgumentException("Source code must start with " + BEGIN_KEYWORD);

        for (int i=1; i<lines.length-1; i++) {
            String line = lines[i];

            if (line.startsWith(INT_KEYWORD)) {
                line = line.substring(INT_KEYWORD.length()).trim(); // Remove "INT" keyword
                int []count =countCommasAndEquals(line);
                if(!(count[0] <= count[1] - 1) && !(count[1]==1)) throw new IllegalArgumentException("Unequal Assignment Operators and Value");
                String[] declarations = line.split(",");
                int def = 0;
                for (String declaration : declarations) {
                    //System.out.println(declaration);
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    def = (parts.length > 1) ? Integer.parseInt(parts[1].trim()) : 0;
                }
                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    int value = (parts.length > 1) ? Integer.parseInt(parts[1].trim()) : def;
                    declareVariable(variableName, value);
                }
            } else if (line.startsWith(FLOAT_KEYWORD)) {
                line = line.substring(FLOAT_KEYWORD.length()).trim(); // Remove "CHAR" keyword
                int []count =countCommasAndEquals(line);
                if(!(count[0] <= count[1] - 1) && !(count[1]==1)) throw new IllegalArgumentException("Unequal Assignment Operators and Value");
                String[] declarations = line.split(",");
                float def = 0;
                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    def = (parts.length > 1) ? Float.parseFloat(parts[1].trim()) : 0;
                }
                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    float value = (parts.length > 1) ? Float.parseFloat(parts[1].trim()) : def;
                    declareVariable(variableName, value);
                }
            } else if (line.startsWith(CHAR_KEYWORD)) {
                line = line.substring(CHAR_KEYWORD.length()).trim(); // Remove "CHAR" keyword
                int []count =countCommasAndEquals(line);
                if(!(count[0] <= count[1] - 1) && !(count[1]==1)) throw new IllegalArgumentException("Unequal Assignment Operators and Value");
                String[] declarations = line.split(",");
                char def = '.';
                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    def = (parts.length > 1) ? parts[1].charAt(1) : 'a';
                }
                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    char value = (parts.length > 1) ? parts[1].charAt(1) : 'a';
                    declareVariable(variableName, value);
                }
            } else if (line.startsWith(BOOL_KEYWORD)) {
                line = line.substring(BOOL_KEYWORD.length()).trim(); // Remove "BOOL" keyword
                int []count =countCommasAndEquals(line);
                if(!(count[0] <= count[1] - 1) && !(count[1]==1)) throw new IllegalArgumentException("Unequal Assignment Operators and Value");
                String[] declarations = line.split(",");
                boolean def = Boolean.parseBoolean(null);
                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    def = (parts.length > 1) ? Boolean.parseBoolean(parts[1]) : def;
                }
                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    boolean value = (parts.length > 1) ? Boolean.parseBoolean(parts[1]) : def;
                    if(!parts[1].equalsIgnoreCase("false") && !value) throw new IllegalArgumentException();
                    declareVariable(variableName, value);
                }
            }else if (line.startsWith(COMMENT_KEYWORD)) {
                // Ignore comments
                continue;
            } else if (line.startsWith(DISPLAY_KEYWORD)) {
                line = line.substring(DISPLAY_KEYWORD.length()).trim(); // Remove "DISPLAY" keyword
                String[] variableNames = line.split("&");

                displayVariables(variableNames);
            } else if (line.startsWith(SCAN_KEYWORD)) {
                String variableNamesString = line.substring(SCAN_KEYWORD.length()).trim();
                String[] variableNames = variableNamesString.split(",");

                for (String variableName : variableNames) {
                    variableName = variableName.trim();
                    // Prompt the user for input
                    System.out.print("Enter value for " + variableName + ": ");
                    // Read user input and assign it to the variable
                    Object value = null;
                    switch (variables.get(variableName).getClass().getSimpleName()) {
                        case "Integer":
                            value = scanner.nextInt();
                            break;
                        case "Character":
                            value = scanner.next().charAt(0);
                            break;
                        case "Boolean":
                            value = scanner.nextBoolean();
                            break;
                        case "Float":
                            value = scanner.nextFloat();
                            break;
                        default:
                            value = null;
                            System.out.println(variables.get(variableName).getClass().getSimpleName());
                            break;
                    }
                    setVariableValue(variableName, value);
                }
            }else if (line.contains("=")) { // Check if the line contains an assignment
                int []count =countCommasAndEquals(line);
                if(!(count[0] <= count[1] - 1) && !(count[1]==1)) throw new IllegalArgumentException("Unequal Assignment Operators and Value");
                String[] declarations = line.split(",");
                Object def = '.';
                for (String declaration : declarations) {
                    //System.out.println(declaration);
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    def = (parts.length > 1) ? Integer.parseInt(parts[1].trim()) : 0;

                }

                for (String declaration : declarations) {
                    String[] parts = declaration.split("=");
                    String variableName = parts[0].trim();
                    Object value = (parts.length > 1) ? (parts[1]) : def;
                    setVariableValue(variableName, value);
                }
            }else if (line.startsWith(ENDIF_KEYWORD)) {
                // Handle END IF or END statement (end of a block)
                // This could involve ending the current scope or similar actions
            }else {
                throw new IllegalArgumentException("Encountered unknown token: " + line);
            }
            // Handle other reserved words similarly
        }
        if(!lines[lines.length-1].trim().equals(END_KEYWORD))
            throw new IllegalArgumentException("Source code must end with " + END_KEYWORD);
    }
}
