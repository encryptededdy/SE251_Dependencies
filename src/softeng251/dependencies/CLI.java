/**
 * Created by Edward Zhang on 30/03/2017.
 * Takes user input and does general main method things
 */
package softeng251.dependencies;
public class CLI {
    public static void main(String[] args) {
        if (args.length != 2) { // in case we don't get filepath and query
            throw new DependenciesException("Incorrect number of input arguments");
        }
        CSV file = new CSV(args[0]);
        file.load();
        // All data is now loaded
        Query query = dispatchQuery(args[1]); // get the right object for the query
        query.setDataSource(file.getDataMap()); // pass the query object all the data
        query.display(); // display the data
        //System.out.println("finished!");
    }
    private static Query dispatchQuery(String query) { // returns the corresponding query class based on arguments
        if (query.equals("Summary")) {
            return new QuerySummary();
        } else if (query.equals("DepCount")) {
            // TODO: Implement the rest of the queries
        } else {
            throw new DependenciesException("Unrecognised Query: "+query);
        }
    }
}