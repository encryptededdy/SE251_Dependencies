package softeng251.dependencies;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Edward Zhang on 31/03/2017.
 */

public class Module {
    private String _pkg;
    private String _kind;
    private HashSet<String> _mod = new HashSet<String>();
    private boolean _noDep;
    ArrayList<Dependency> _dependencyList = new ArrayList<Dependency>();

    public Module(String pkg, String kind, String mod, boolean noDep) {
        _pkg = pkg;
        _kind = kind;
        _mod = mod;//todo make this a HashSet
        _noDep = noDep;
    }
    public void add(String[] dataarray) {
        if (_noDep) {
            // you can't add a dependency if there's no dependencies. throw an exception or something
        } else {
            _dependencyList.add(new Dependency());
            // TODO: Process the dependencies into the class
        }
    }
}
