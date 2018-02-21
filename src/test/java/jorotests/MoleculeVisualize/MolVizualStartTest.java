package jorotests.MoleculeVisualize;


import com.fasterxml.jackson.databind.ObjectMapper;
import pu.reactor.workspace.Preferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by gogo on 30.1.2018 г..
 */
public class MolVizualStartTest {
  public static ArrayList<String> list;
    public static void main(String[] args) throws Exception {
        list = loadFromJSON(new File("./starting-materials.txt"));
        MainFrame frame = new MainFrame(list);
        frame.setVisible(true);

    }
    public static ArrayList<String> loadFromJSON(File jsonFile) throws Exception
    {
        ArrayList list = new ArrayList();
        // Open the file
        FileInputStream fstream = new FileInputStream(jsonFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

//Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console

            list.add(strLine);
        }

//Close the input stream
        br.close();
        return list;
    }

}
