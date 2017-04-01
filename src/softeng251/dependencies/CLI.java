/**
 * Created by Edward Zhang on 30/03/2017.
 * Takes user input and does general main method things
 */
package softeng251.dependencies;

import java.io.File;

public class CLI {
    public static void main(String[] args) {
        if (args.length != 2) { // in case we don't get filepath and query
            throw new DependenciesException("Incorrect number of input arguments");
        }
        File filepath = new File(args[0]);
        CSV file = new CSV(filepath);
        file.load();
        // All data is now loaded
        Query query = dispatchQuery(args[1]); // get the right object for the query
        query.setDataSource(file); // pass the query object all the data
        query.display(); // display the data
        //System.out.println("finished!");
    }
    private static Query dispatchQuery(String query) { // returns the corresponding query class based on arguments
        if (query.equals("Summary")) {
            return new QuerySummary();
        } else if (query.equals("DepCount")) {
            return new QueryDepCount();
        } else {
            throw new DependenciesException("Unrecognised Query: "+query);
        }
    }
}