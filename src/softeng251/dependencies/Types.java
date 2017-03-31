package softeng251.dependencies;

/**
 * All the different custom types(enums) used in Dependencies
 * Created by Edward Zhang on 31/03/2017.
 */

public class Types {
    public enum Kind {
        CLASS,
        INTERFACE,
        ENUM,
        ANNOTATION,
        EXCEPTION,
        PRIMITIVE,
        UNKNOWN;
    }
    public enum Modifier {
        ABSTRACT,
        FINAL,
        NATIVE,
        PRIVATE,
        PROTECTED,
        PUBLIC,
        STRICTFP,
        STATIC,
        SYNCHRONIZED,
        TRANSIENT,
        VOLATILE,
        SYNTHETIC;
    }
}
