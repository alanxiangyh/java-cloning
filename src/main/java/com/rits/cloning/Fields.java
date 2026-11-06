package com.rits.cloning;


import com.rits.cloning.exception.CloningException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * {@link Accessor Functions} for {@link #ACCESSOR accessing} {@link Field}s.
 *
 * <p>The access method can be specified via the {@code com.rits.cloning.Fields.accessor} system-property setting to:
 * <ul>
 *     <li>{@code auto} - (default) auto-select best available accessor</li>
 *     <li>{@code handles} - fast, but requires {@code --add-opens} to access JDK internal fields; falls back on {@code reflection} to set {@code final}s</li>
 *     <li>{@code reflection} - legacy approach, but requires {@code --add-opens} to access JDK internal fields</li>
 * </ul>
 *
 * <p>These APIs are considered low level and is intentionally restricted to package-private access. Mismatching objects
 * and fields, or {@link Accessor#getCookie cookies} can lead to undefined behavior.
 *
 * @author mark.falco
 * @since December 2024
 */
class Fields {

    /**
     * The configured {@link Accessor} for accessing fields.
     */
    static final Accessor<Object> ACCESSOR = AccessorType.resolve(AccessorType.valueOf(System.getProperty(
        Fields.class.getName() + ".accessor", AccessorType.HANDLES.name()).trim().toUpperCase()));

    /**
     * Blocked constructor.
     */
    private Fields() {
    }

    static <T> T handleWithContext(Field field, Supplier<T> action) {
        try {
            return action.get();
        } catch (SecurityException | IllegalArgumentException e) {
            throw cloningExceptionFor(field, e);
        } catch (CloningException e) {
            throw cloningExceptionFor(field, e.getCause());
        }
    }

    static void handleWithContext(Field field, ThrowingRunnable action) {
        try {
            action.run();
        } catch (SecurityException | IllegalArgumentException | ReflectiveOperationException e) {
            throw cloningExceptionFor(field, e);
        } catch (CloningException e) {
            throw cloningExceptionFor(field, e.getCause());
        }
    }

    private static CloningException cloningExceptionFor(Field field, Throwable t) {
        return new CloningException(String.format("No access to field [%s] [%s] within class [%s]", field.getType(), field.getName(), field.getDeclaringClass()), t);
    }

    /**
     * Available {@link Accessor} types.
     */
    private enum AccessorType {
        AUTO,
        HANDLES,
        REFLECTION;

        /**
         * @return the {@link Accessor} of the specified {@link AccessorType} or closest available match.
         */
        @SuppressWarnings("rawtypes")
        private static Accessor<Object> resolve(AccessorType type) {
            return switch (type) {
                case AUTO, HANDLES -> (Accessor) VarHandleAccessor.INSTANCE;
                case REFLECTION -> (Accessor) ReflectionAccessor.INSTANCE;
            };
        }
    }

    /**
     * Interface for accessing fields of objects.
     *
     * @param <C> the cookie type for this accessor
     */
    interface Accessor<C> {

        /**
         * Return the cookie for a given field.
         *
         * @param field the field to get a cookie for
         * @return the cookie
         */
        C getCookie(Field field);

        /**
         * Return the specified field from the source object.
         *
         * @param field  the field to get
         * @param cookie the field {@link #getCookie cookie}
         * @param src    the object to get the field value from
         * @return the field value
         */
        Object get(Field field, C cookie, Object src) throws IllegalAccessException;

        /**
         * Set the specified field in the destination object.
         *
         * @param field  the field to set
         * @param cookie the field {@link #getCookie cookie}
         * @param dest    the object to set the field in
         * @param value  the new value for the field
         */
        void set(Field field, C cookie, Object dest, Object value) throws IllegalAccessException;

        /**
         * Copy (shallow clone) the specified field from the source to destination object.
         *
         * @param field  the field to copy
         * @param cookie the field {@link #getCookie cookie}
         * @param src    the source object
         * @param dest    the destination object
         */
        void copy(Field field, C cookie, Object src, Object dest) throws IllegalAccessException;
    }

    @FunctionalInterface
    interface ThrowingRunnable {

        void run() throws IllegalAccessException;
    }

    /**
     * Legacy reflection based {@link Accessor}.
     */
    private static class ReflectionAccessor implements Accessor<Field> {

        private static final ReflectionAccessor INSTANCE = new ReflectionAccessor();

        @Override
        public Field getCookie(Field field) {
            handleWithContext(field, field::trySetAccessible);
            return field;
        }

        @Override
        public Object get(Field field, Field cookie, Object src) throws IllegalAccessException {
            return cookie.get(src);
        }

        @Override
        public void set(Field field, Field cookie, Object dest, Object value) throws IllegalAccessException {
            cookie.set(dest, value);
        }

        @Override
        public void copy(Field field, Field cookie, Object src, Object dest) throws IllegalAccessException {
            Class<?> t = field.getType();
            if (!t.isPrimitive()) cookie.set(dest, cookie.get(src));
            else if (t == int.class) cookie.setInt(dest, cookie.getInt(src));
            else if (t == long.class) cookie.setLong(dest, cookie.getLong(src));
            else if (t == boolean.class) cookie.setBoolean(dest, cookie.getBoolean(src));
            else if (t == double.class) cookie.setDouble(dest, cookie.getDouble(src));
            else if (t == float.class) cookie.setFloat(dest, cookie.getFloat(src));
            else if (t == char.class) cookie.setChar(dest, cookie.getChar(src));
            else if (t == byte.class) cookie.setByte(dest, cookie.getByte(src));
            else if (t == short.class) cookie.setShort(dest, cookie.getShort(src));
            else cookie.set(dest, cookie.get(src));
        }
    }

    /**
     * {@link VarHandle} implementation of {@link Accessor} avoiding per invocation access checks made with reflection.
     */
    private static class VarHandleAccessor implements Accessor<VarHandleAccessor.HandleCache> {

        record HandleCache(VarHandle getter, MethodHandle setter) {}

        private static final VarHandleAccessor INSTANCE = new VarHandleAccessor();

        /**
         * Mapping of fields to their {@link VarHandle} for a given class.
         */
        private final ClassValue<Map<Field, HandleCache>> handleByField = new ClassValue<>() {
            /**
             * The {@link MethodHandles.Lookup} used to find {@link VarHandle}s.
             */
            private final MethodHandles.Lookup lookup = MethodHandles.lookup();

            @Override
            protected Map<Field, HandleCache> computeValue(Class<?> clz) {
                Map<Field, HandleCache> map = new HashMap<>();
                try {
                    MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clz, this.lookup);
                    for (Field f : clz.getDeclaredFields()) {
                        if (Modifier.isStatic(f.getModifiers())) {
                            // Why copy static fields???
                            continue;
                        }
                        f.trySetAccessible();
                        map.put(f, new HandleCache(lookup.unreflectVarHandle(f), lookup.unreflectSetter(f)));
                    }
                } catch (IllegalAccessException e) {
                    throw new CloningException(String.format("No access to class %s from context %s", clz.getName(), lookup.lookupClass().getModule()), e);
                }

                return map;
            }
        };

        @Override
        public HandleCache getCookie(Field field) {
            return handleWithContext(field, () -> handleByField.get(field.getDeclaringClass()).get(field));
        }

        @Override
        public Object get(Field field, HandleCache handle, Object src) {
            boolean isVolatile = Modifier.isVolatile(field.getModifiers());
            /* Why copy a static field???
            return src == null
                ? v ? h.getVolatile() : h.get()
                : v ? h.getVolatile(src) : h.get(src);

             */
            return isVolatile ? handle.getter().getVolatile(src) : handle.getter().get(src);
        }

        @Override
        public void set(Field field, HandleCache handle, Object dest, Object value) throws IllegalAccessException {
            /* We can do this more clever...
            // note we don't need volatile writes during cloning as dest is not yet visible to other threads
            if (Modifier.isFinal(field.getModifiers())) { // VarHandle can't update finals; fall back on reflection
                ReflectionAccessor.INSTANCE.set(field, ReflectionAccessor.INSTANCE.getCookie(field), dst, value);
            } else if (dest == null) {
                h.set(value);
            } else {
                h.set(dest, value);
            }
             */
            try {
                handle.setter().invoke(dest, value);
            } catch (Throwable t) {
                //TODO make exception handle better
                t.printStackTrace();
                throw new IllegalAccessException();
            }
        }

        @Override
        public void copy(Field field, HandleCache handle, Object src, Object dest) throws IllegalAccessException {
           set(field, handle, dest, get(field, handle, src));
            /* Why not KISS?
            int mods = field.getModifiers();
            if (Modifier.isFinal(mods)) { // VarHandle can't update finals; fall back on reflection
                ReflectionAccessor.INSTANCE.copy(field, ReflectionAccessor.INSTANCE.getCookie(field), src, dest);
                return;
            }

            // note: we don't need volatile writes during cloning as dest is not yet visible to other threads; volatile
            // reads are still performed as the source may be visible to other threads
            Class<?> t = field.getType();
            boolean v = Modifier.isVolatile(mods);

            // the seemingly needless casts allow VarHandle to optimize out the autoboxing and its garbage
            if (!t.isPrimitive()) handle.set(dest, v ? handle.getVolatile(src) : handle.get(src));
            else if (t == int.class) handle.set(dest, v ? (int) handle.getVolatile(src) : (int) handle.get(src));
            else if (t == long.class) handle.set(dest, v ? (long) handle.getVolatile(src) : (long) handle.get(src));
            else if (t == boolean.class) handle.set(dest, v ? (boolean) handle.getVolatile(src) : (boolean) handle.get(src));
            else if (t == double.class) handle.set(dest, v ? (double) handle.getVolatile(src) : (double) handle.get(src));
            else if (t == float.class) handle.set(dest, v ? (float) handle.getVolatile(src) : (float) handle.get(src));
            else if (t == char.class) handle.set(dest, v ? (char) handle.getVolatile(src) : (char) handle.get(src));
            else if (t == byte.class) handle.set(dest, v ? (byte) handle.getVolatile(src) : (byte) handle.get(src));
            else if (t == short.class) handle.set(dest, v ? (short) handle.getVolatile(src) : (short) handle.get(src));
            else handle.set(dest, v ? handle.getVolatile(src) : handle.get(src));

             */
        }
    }
}