package softeng251.dependencies.data;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Edward Zhang on 30/03/2017.
 * Stores the data from the CSV
 */

public class CSV extends HashMap<String, Module>{
    private File _filepath;

    public CSV(File filepath) {
        _filepath = filepath;
    }

    public void printFileName(){
        System.out.println("DATAID\t"+_filepath.getName());
    }
}

