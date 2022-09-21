package parser;

/**
 * A class for an object that optionally has a name.
 */
public class OptionallyNamed {

    // this object's name
    private final String name;

    /**
     * A constructor to initialise this object without a name.
     */
    public OptionallyNamed() {
        this(null);
    }

    /**
     * A constructor to initialise this object's name.
     * @param name this object's name
     */
    public OptionallyNamed(String name) {
        this.name = name;
    }

    /**
     * @return this object's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves this object's string representation.
     * If `name` is null then the representation is that of the super object. Otherwise, it is this object's name.
     * @return this object's string representation
     */
    @Override
    public String toString() {
        // We return the super object's string representation iff this object does not have a name.
        if (name == null) {
            return super.toString();
        }
        // Otherwise, we return this object's name.
        return name;
    }
}
