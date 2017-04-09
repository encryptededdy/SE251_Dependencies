package softeng251.dependencies.query;

import softeng251.dependencies.data.CSV;
import softeng251.dependencies.DependenciesException;
import softeng251.dependencies.data.Module;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Edward Zhang on 1/04/2017.
 * Query handler for DepCount
 */

public class DepCount implements Query {
    private CSV _data;
    private NavigableMap<String, String> dependencies = new TreeMap<>(new AlphaNumericComparator()); // Use our custom comparator on the TreeMap
    private int _printed = 0;
    public void display() {
        if(_data == null) {
            throw new DependenciesException("Cannot execute display() without data source set!");
        }
        populateTree();
        // print the data in a sorted order (TreeMap remains sorted)
        for (String entry : dependencies.values()) { // iterate through the outer Map (custom comparator)
            System.out.println(entry); // print the value (already formatted)
            _printed++;
        }
        if (_printed == 0) {
            System.out.println("No results");
        }
    }

    public void setDataSource(CSV data) {
        _data = data;
    }

    private void populateTree() {
        for (Map.Entry<String, Module> entry : _data.entrySet()) { // loop through modules
            int depCount = entry.getValue().size();
            String source = entry.getKey();
            String kind = entry.getValue().getKind().toString();
            if (depCount > 0) { // only bother if the module actually has dependencies
                dependencies.put(String.format("%d %s", depCount, source), String.format("%s (%s)\t%d", source, kind, depCount)); // write both the string and depCount to the key for comparison reasons. Value is preformatted output.
            }
        }
    }
    class AlphaNumericComparator implements Comparator<String> { // This is a custom comparator for sorting numerically first then alphabetically
        public int compare(String o1, String o2) {
            String[] o1Split = o1.split(" ");
            String[] o2Split = o2.split(" ");
            // string[0] is the number, string[1] is the source name
            Integer o1Num = Integer.parseInt(o1Split[0]); // first half of the string is the number, second half is str
            Integer o2Num = Integer.parseInt(o2Split[0]);
            if (!o1Num.equals(o2Num)) { // If the numbers are different, then just do a standard numeric int comparison
                return o2Num.compareTo(o1Num); // do in reverse to attain descending rather than ascending order
            } else { // otherwise if they are the same we need to do an alphabetical comparison
                return o1Split[1].compareTo(o2Split[1]);
            }
        }
    }
}
