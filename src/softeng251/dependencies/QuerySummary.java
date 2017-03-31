package softeng251.dependencies;

import java.util.Map;

/**
 * Created by Edward Zhang on 1/04/2017.
 */

public class QuerySummary implements Query {
    private Map<String, Module> _datamap;
    public void display() {
        //TODO
    }
    public void setDataSource(Map<String, Module> datamap) {
        _datamap = datamap;
    }
}
