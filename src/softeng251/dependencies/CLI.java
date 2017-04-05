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
        CSVReader file = new CSVReader(filepath);
        CSV data = file.load();
        // All data is now loaded into "data"
        Query query = dispatchQuery(args[1]); // get the right object for the query
        query.setDataSource(data); // pass the query object all the data
        printHeader(args[1], data); // display the info about the query
        query.display(); // display the data
        //TODO: Implement "no results" output
        System.out.println("test"); // delet dis
    }

    private static Query dispatchQuery(String query) { // returns the corresponding query class based on arguments
        switch(query) {
            case "Summary":
                return new QuerySummary();
            case "DepCount":
                return new QueryDepCount();
            case "FanOut":
                return new QueryFanOut();
            case "FanIn":
                return new QueryFanIn();
            case "Uses":
                return new QueryCategoryCheck(new String[]{"invoke", "field"});
            case "Static":
                return new QueryCategoryCheck("static");
            case "Aggregates":
                return new QueryCategoryCheck("field");
            default:
                throw new DependenciesException("Unrecognised Query: "+query);
        }
    }

    private static void printHeader(String query, CSV data) {
        System.out.println("QUERY\t"+query);
        data.printFileName();
    }
}