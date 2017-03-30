package softeng251.dependencies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Edward Zhang on 30/03/2017.
 * Reads the input CSV file
 */
public class CSV {
    private String _filepath;
    private HashMap<String, Module> _datamap = new HashMap(); // creates the hashmap that'll store all the data
    public CSV(String filepath) {
        _filepath = filepath;
    }
    public void load(){ // returns whether load was successful
        String fileln;
        try(BufferedReader file = new BufferedReader(new FileReader(_filepath))) {
            // read the file line by line
            while((fileln = file.readLine()) != null) {
                if (fileln.length() > 0 && fileln.charAt(0) != '#') { // make sure line isn't commented
                    storeline(fileln);
                }
            }
        } catch (IOException exception) {
            System.err.println("Couldn't read file. Check filename and try again");
            exception.printStackTrace();
            System.exit(0);
        }
    }
    private void storeline(String line) {
        String[] splitline = line.split("\t");
        boolean noDep = (splitline.length == 4); // check if it has dependencies

        if (!(_datamap.containsKey(splitline[0]))) { // create a entry in the hashmap if this module isn't already in there
            _datamap.put(splitline[0], new Module(splitline[1], splitline[2], splitline[3], noDep));
        }
        Module currentModule = _datamap.get(splitline[0]); // get the Module we want to add to
        if (!noDep) {
            currentModule.add(Arrays.copyOfRange(splitline, 4, splitline.length)); // add the new dependency
        }
        System.out.println("Adding: " + splitline[0]); // DEBUG
    }
}

