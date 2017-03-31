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
        System.out.println("finished!");
    }
}
