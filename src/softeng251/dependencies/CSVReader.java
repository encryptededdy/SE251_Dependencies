package softeng251.dependencies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Edward Zhang on 4/04/2017.
 * Reads the input CSV file
 */
public class CSVReader {
    private File _filepath;
    private CSV _data; // CSV will store all the data
    private int _lineNo = 0; // used for more informative exceptions (to report what line of the CSV we encountered an error on)

    public CSVReader(File filepath) {
        _filepath = filepath;
        _data = new CSV(filepath);
    }

    public CSV load(){ // returns whether load was successful
        String fileln;
        try(BufferedReader file = new BufferedReader(new FileReader(_filepath))) {
            // read the file line by line
            while((fileln = file.readLine()) != null) {
                _lineNo++; // Track the line number: Useful for errors!
                if (fileln.length() > 0 && fileln.charAt(0) != '#') { // make sure line isn't commented
                    storeline(fileln);
                }
            }
        } catch (IOException exception) {
            throw new DependenciesException("CSV file could not be read", exception);
        }
        return _data;
    }

    private void storeline(String line) {
        String[] splitline = line.split("\t");
        String mod = "";

        if (splitline.length != 3 && splitline.length != 4 && splitline.length != 15) { // 3: No dependencies, no modifiers. 4: No dependencies. 15: with Dependencies
            throw new DependenciesException("Error at CSV line "+_lineNo+": File format unsupported: Invalid number of columns ("+splitline.length+").");
        }

        boolean noDep = (splitline.length < 15); // check if it has dependencies. No dependencies if we don't have 15 cols of data.

        if (!noDep) {
            _data.depFound(); // if the module does have dependencies, increment our counter for dependencies
        }

        if (!(_data.containsKey(splitline[0]))) { // create a entry in the hashmap if this module isn't already in there
            if (splitline.length > 3) {
                mod = splitline[3]; // only try to read the modifier if there is one. Otherwise we will access out of bounds
            }
            _data.put(splitline[0], new Module(splitline[1], splitline[2], mod));
        }

        Module currentModule = _data.get(splitline[0]); // get the Module we want to add to

        if (!noDep) {
            currentModule.add(Arrays.copyOfRange(splitline, 4, splitline.length)); // add the new dependency
        } else {
            currentModule.add(new String[0]); // set a flag indicating that there is a instance of this module that doesn't have any dependencies
        }
    }
}
