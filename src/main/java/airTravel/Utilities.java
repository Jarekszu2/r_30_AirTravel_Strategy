package airTravel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public List<String> getStringFromFile(String file) {
        List<String> listFromFile = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)))) {
            bufferedReader.lines().forEach(listFromFile::add);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listFromFile;
    }
}
