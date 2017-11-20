package fr.xgouchet.mesmaeker.core;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Xavier F. Gouchet
 */
public class ValidatingUtils {

    public static <T, O> void verifyFunctionThrows(T subject, Function<T, O> function, Throwable expected) {
        boolean match = false;
        Throwable thrown = null;
        try {
            function.apply(subject);
        } catch (Throwable t) {
            thrown = t;
            match = expected.getClass().isAssignableFrom(t.getClass());
        }

        if (thrown == null) {
            throw new AssertionError("Expecting " + expected.getClass().getSimpleName() + " to be thrown");
        } else if (!match) {
            throw new AssertionError("Expecting " + expected.getClass().getSimpleName() + " to be thrown, but was " + thrown.getClass().getSimpleName());
        }
    }

    public static <T, O> void verifyFunctionThrows(T subject, Function<T, O> function, Class<? extends Throwable> expected) {
        boolean match = false;
        Throwable thrown = null;
        try {
            function.apply(subject);
        } catch (Throwable t) {
            thrown = t;
            match = expected.isAssignableFrom(t.getClass());
        }

        if (thrown == null) {
            throw new AssertionError("Expecting " + expected.getSimpleName() + " to be thrown");
        } else if (!match) {
            throw new AssertionError("Expecting " + expected.getSimpleName() + " to be thrown, but was " + thrown.getClass().getSimpleName());
        }
    }

    public static <T, O> void verifyConsumerThrows(T subject, Consumer<T> consumer, Class<? extends Throwable> expected) {
        boolean match = false;
        Throwable thrown = null;
        try {
            consumer.accept(subject);
        } catch (Throwable t) {
            thrown = t;
            match = expected.isAssignableFrom(t.getClass());
        }

        if (thrown == null) {
            throw new AssertionError("Expecting " + expected.getSimpleName() + " to be thrown");
        } else if (!match) {
            throw new AssertionError("Expecting " + expected.getSimpleName() + " to be thrown, but was " + thrown.getClass().getSimpleName());
        }
    }
}
