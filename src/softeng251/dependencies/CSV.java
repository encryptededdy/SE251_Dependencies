package softeng251.dependencies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Edward Zhang on 30/03/2017.
 * Reads the input CSV file
 */
public class CSV {
    private static final List<String> _allowedExtensions = Arrays.asList("csv", "txt"); // define the allowed filetypes
    private String _filepath;
    private Map<String, Module> _datamap = new HashMap<String, Module>(); // creates the hashmap that'll store all the data

    public CSV(String filepath) {
        int index = filepath.lastIndexOf('.');
        String extension = filepath.substring(index+1);
        if (_allowedExtensions.contains(extension)) { // make sure that the incoming file has the correct extensions
            _filepath = filepath;
        } else {
            throw new DependenciesException("Invalid file extension. Allowed Extensions: "+_allowedExtensions.toString());
        }
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
            throw new DependenciesException("CSV file could not be read", exception);
        }
    }

    private void storeline(String line) {
        String[] splitline = line.split("\t");
        if (splitline.length != 4 && splitline.length != 15) {
            throw new DependenciesException("File format unsupported: Invalid number of columns ("+splitline.length+")");
        }
        boolean noDep = (splitline.length == 4); // check if it has dependencies

        if (!(_datamap.containsKey(splitline[0]))) { // create a entry in the hashmap if this module isn't already in there
            _datamap.put(splitline[0], new Module(splitline[1], splitline[2], splitline[3], noDep));
        }
        Module currentModule = _datamap.get(splitline[0]); // get the Module we want to add to
        if (!noDep) {
            currentModule.add(Arrays.copyOfRange(splitline, 4, splitline.length)); // add the new dependency
        }
    }
}

