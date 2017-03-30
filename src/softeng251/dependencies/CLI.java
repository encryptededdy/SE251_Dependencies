/**
 * Created by Edward Zhang on 30/03/2017.
 */
package softeng251.dependencies;
public class CLI {
    public static void main(String[] args) {
        if (args.length != 2) { // in case we don't get filepath and query
            System.err.println("Invalid Arguments");
            System.exit(0); // no point continuing
        }
        CSV file = new CSV(args[0]);
        file.load();
    }
}
