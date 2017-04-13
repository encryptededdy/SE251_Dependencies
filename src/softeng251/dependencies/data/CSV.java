package softeng251.dependencies.data;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Edward Zhang on 30/03/2017.
 * Stores the data from the CSV
 */

public class CSV extends HashMap<String, Module>{
    private File _file; // Stores information about the file that this CSV object represents

    public CSV(File file) {
        _file = file;
    }

    public void printFileName(){
        System.out.println("DATAID\t"+_file.getName());
    } // Prints the filename
}

