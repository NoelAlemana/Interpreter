import java.util.*;
import java.io.*;

public class Main {

    // Main method
    public static void main(String[] args) {
        // Define the filename containing the CODE program
        String filename = "src/sourceCode.txt";

        try {
            // Create a FileReader to read the file
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Create a StringBuilder to store the CODE program
            StringBuilder codeBuilder = new StringBuilder();
            String line;
            // Read each line from the file and append it to the StringBuilder
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                codeBuilder.append(line).append("\n");
            }

            // Close the BufferedReader
            bufferedReader.close();

            // Create an instance of CODEInterpreter
            Interpreter interpreter = new Interpreter();

            // Run the interpreter on the CODE program
            System.out.println("Source Code:-----------------");
            System.out.println(codeBuilder.toString());
            System.out.println("End of Source Code----------");
            interpreter.interpret(codeBuilder.toString());
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

    }
}
