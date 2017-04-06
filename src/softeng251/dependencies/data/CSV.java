package softeng251.dependencies.data;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Edward Zhang on 30/03/2017.
 * Stores the data from the CSV
 */

public class CSV extends HashMap<String, Module>{
    private File _filepath;
    private int _depCount = 0; // counts the number of dependencies that are stored

    public CSV(File filepath) {
        _filepath = filepath;
    }

    public void depFound() {
        _depCount++;
    }

    public int getDepCount(){
        return _depCount;
    }

    public void printFileName(){
        System.out.println("DATAID\t"+_filepath.getName());
    }
}

