package fr.xgouchet.mesmaeker.mockito;

import org.mockito.Mockito;

import java.util.function.Function;

/**
 * @author Xavier F. Gouchet
 */
public class MockitoOngoingClause<T, O> implements OngoingClause<T, O> {

    private final T subject;
    private final Function<T, O> function;

    MockitoOngoingClause(T instance, Function<T, O> function) {
        this.subject = instance;
        this.function = function;
    }

    @Override
    public OngoingClause<T, O> thenReturn(O value) {
        Mockito.when(function.apply(subject)).thenReturn(value);
        return this;
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"}) @Override
    public OngoingClause<T, O> thenReturn(O value, O... values) {
        Mockito.when(function.apply(subject)).thenReturn(value, values);
        return this;
    }

    @Override
    public OngoingClause<T, O> thenThrow(Throwable... throwables) {
        Mockito.when(function.apply(subject)).thenThrow(throwables);
        return this;
    }

    @Override
    public OngoingClause<T, O> thenThrow(Class<? extends Throwable> throwableType) {
        Mockito.when(function.apply(subject)).thenThrow(throwableType);
        return this;
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"}) @Override
    public OngoingClause<T, O> thenThrow(Class<? extends Throwable> toBeThrown, Class<? extends Throwable>... nextToBeThrown) {
        Mockito.when(function.apply(subject)).thenThrow(toBeThrown, nextToBeThrown);
        return this;
    }
}
