package softeng251.dependencies;

/**
 * Created by Edward Zhang on 31/03/2017.
 */
public class DependenciesException extends RuntimeException {
    public DependenciesException(String msg) {
        super(msg);
    }
    public DependenciesException(String msg, Throwable exp) { // for when we pass another exception here
        super(msg, exp);
    }
}
