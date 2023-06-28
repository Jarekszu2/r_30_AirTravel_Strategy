package airTravel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public List<String> getListStringFromEntireFile(String file) {
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

    public List<List<String>> getListStringFromSomeLinesFromFile(String file) {
        String line;
        List<List<String>> mainList = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            List<String> strings = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("-")){
                    mainList.add(strings);
                    strings = new ArrayList<>();
                }
                if (!line.startsWith("-")){
                    strings.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainList.remove(0);
        return mainList;
    }
}
