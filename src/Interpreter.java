import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
public class Interpreter {
    private HashMap<String, Object> variables;
    private Scanner scanner;

    // Define reserved words as constants
    private static final String INT_KEYWORD = "INT";
    private static final String DISPLAY_KEYWORD = "DISPLAY:";
    private static final String CHAR_KEYWORD = "CHAR";
    private static final String BOOL_KEYWORD = "BOOL";
    private static final String FLOAT_KEYWORD = "FLOAT";
    private static final String SCAN_KEYWORD = "SCAN:";
    private static final String IF_KEYWORD = "IF";
    private static final String END_KEYWORD = "END IF";
    private static final String BEGINWHILE_KEYWORD = "BEGIN WHILE";
    private static final String ENDWHILE_KEYWORD = "END WHILE";
    private static final String COMMENT_KEYWORD = "#";

    public Interpreter() {
        variables = new HashMap<>();
        scanner = new Scanner(System.in);
    }

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
            if (variables.containsKey(variableName)) {
                System.out.print(variableName + " = " + getVariableValue(variableName));
            } else {
                System.err.println("Error: Variable " + variableName + " not found");
            }
        }
    }

    public void interpret(String sourceCode) {
        String[] lines = sourceCode.split("\n");
        for (String line : lines) {
            if (line.startsWith(INT_KEYWORD)) {
                line = line.substring(INT_KEYWORD.length()).trim(); // Remove "INT" keyword
                String[] declarations = line.split(",");
                int def = 0;
                for (String declaration : declarations) {
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
                String[] variableNames = line.split(" ");
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
                String[] parts = line.split("="); // Split the line by "="
                String variableName = parts[0].trim();
                String[] variableNames = variableName.split(",");
                String valueString = parts[1].trim();
                if(parts.length >2) throw new IllegalArgumentException("Unequal Assignment Operators and Value");
                // Check if the variable exists
                for(String var : variableNames){
                    if (!variables.containsKey(var)) {
                        throw new IllegalArgumentException("Variable " + var + " does not exist");
                    }
                    // Determine the data type of the variable and parse the value accordingly
                    Object value;
                    if (variables.get(var) instanceof Integer) {
                        value = Integer.parseInt(valueString);
                    } else if (variables.get(var) instanceof Float) {
                        value = Float.parseFloat(valueString);
                    } else if (variables.get(var) instanceof Character) {
                        value = valueString.charAt(0);
                    } else if (variables.get(var) instanceof Boolean) {
                        value = Boolean.parseBoolean(valueString);
                    } else {
                        throw new IllegalArgumentException("Unknown data type for variable " + var);
                    }
                    // Set the new value for the variable
                    setVariableValue(var, value);
                }
            }else if (line.startsWith(END_KEYWORD)) {
                // Handle END IF or END statement (end of a block)
                // This could involve ending the current scope or similar actions
            }
            // Handle other reserved words similarly
        }
    }
}