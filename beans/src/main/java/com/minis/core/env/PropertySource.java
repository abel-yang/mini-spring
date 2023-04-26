package com.minis.core.env;

import com.minis.beans.util.ObjectUtils;
import com.minis.lang.Nullable;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/24 14:21
 */
public abstract class PropertySource<T> {

    protected final String name;
    protected final T source;

    public PropertySource(String name) {
        this.name = name;
        this.source = (T) new Object();
    }

    public PropertySource(String name, T source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public T getSource() {
        return source;
    }

    /**
     * Return whether this {@code PropertySource} contains the given name.
     * <p>This implementation simply checks for a {@code null} return value
     * from {@link #getProperty(String)}. Subclasses may wish to implement
     * a more efficient algorithm if possible.
     * @param name the property name to find
     */
    public boolean containsProperty(String name) {
        return (getProperty(name) != null);
    }

    protected abstract Object getProperty(String name);


    /**
     * Return a {@code PropertySource} implementation intended for collection comparison purposes only.
     * <p>Primarily for internal use, but given a collection of {@code PropertySource} objects, may be
     * used as follows:
     * <pre class="code">
     * {@code List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
     * sources.add(new MapPropertySource("sourceA", mapA));
     * sources.add(new MapPropertySource("sourceB", mapB));
     * assert sources.contains(PropertySource.named("sourceA"));
     * assert sources.contains(PropertySource.named("sourceB"));
     * assert !sources.contains(PropertySource.named("sourceC"));
     * }</pre>
     * The returned {@code PropertySource} will throw {@code UnsupportedOperationException}
     * if any methods other than {@code equals(Object)}, {@code hashCode()}, and {@code toString()}
     * are called.
     * @param name the name of the comparison {@code PropertySource} to be created and returned.
     */
    public static PropertySource<?> named(String name) {
        return new ComparisonPropertySource(name);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj instanceof PropertySource &&
                ObjectUtils.nullSafeEquals(this.name, ((PropertySource<?>)obj).name));
    }

    /**
     * {@code PropertySource} to be used as a placeholder in cases where an actual
     * property source cannot be eagerly initialized at application context
     * creation time.  For example, a {@code ServletContext}-based property source
     * must wait until the {@code ServletContext} object is available to its enclosing
     * {@code ApplicationContext}.  In such cases, a stub should be used to hold the
     * intended default position/order of the property source, then be replaced
     * during context refresh.
     * @see org.springframework.context.support.AbstractApplicationContext#initPropertySources()
     * @see org.springframework.web.context.support.StandardServletEnvironment
     * @see org.springframework.web.context.support.ServletContextPropertySource
     */
    public static class StubPropertySource extends PropertySource<Object> {

        public StubPropertySource(String name) {
            super(name, new Object());
        }

        /**
         * Always returns {@code null}.
         */
        @Override
        @Nullable
        public String getProperty(String name) {
            return null;
        }
    }


    /**
     * A {@code PropertySource} implementation intended for collection comparison
     * purposes.
     *
     * @see PropertySource#named(String)
     */
    static class ComparisonPropertySource extends StubPropertySource {

        private static final String USAGE_ERROR =
                "ComparisonPropertySource instances are for use with collection comparison only";

        public ComparisonPropertySource(String name) {
            super(name);
        }

        @Override
        public Object getSource() {
            throw new UnsupportedOperationException(USAGE_ERROR);
        }

        @Override
        public boolean containsProperty(String name) {
            throw new UnsupportedOperationException(USAGE_ERROR);
        }

        @Override
        @Nullable
        public String getProperty(String name) {
            throw new UnsupportedOperationException(USAGE_ERROR);
        }
    }
}
