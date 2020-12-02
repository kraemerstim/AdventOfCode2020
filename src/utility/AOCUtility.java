package utility;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AOCUtility {

    public static ArrayList<String> readFile(String fileName) {
        var lines = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
