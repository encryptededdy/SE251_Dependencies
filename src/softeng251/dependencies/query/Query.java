package softeng251.dependencies.query;

import softeng251.dependencies.data.CSV;

/**
 * Created by Edward Zhang on 1/04/2017.
 * A interface for the different query classes which handle displaying the data
 */
public interface Query {
    void display(); // basically "executes" the query
    void setDataSource(CSV data);  // we don't put this in constructor to minimise duplicate code
}
