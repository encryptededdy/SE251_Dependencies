package softeng251.dependencies.data;

/**
 * All the different custom types(enums) used in Dependencies
 * Created by Edward Zhang on 31/03/2017.
 */

public class Types {
    public enum Kind {
        Class,
        Interface,
        Enum,
        Annotation,
        Exception,
        Primitive,
        Unknown
    }

    // Modifier data processing implemented but not used for the purposes of this Assignment
    /*public enum Modifier {
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
    }*/
}
