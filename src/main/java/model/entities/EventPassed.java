package model.entities;

/**
 * <p>Allows you to Accept custom type
 *
 * <p>Example:
 * <blockquote>
 *     <pre>
 * new EventPassed<>("String");
 * new EventPassed<>(2);
 * new EventPassed<>(new Object);
 *     </pre>
 * </blockquote>
 * @param <T> Accepts a Custom Type
 */
final public class EventPassed<T> {
    private T type;

    public EventPassed(T t) {
        type = t;
    }

    public T getType() {
        return type;
    }
}
