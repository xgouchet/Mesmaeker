package fr.xgouchet.mesmaeker.mockito;

import java.util.function.Function;

import static fr.xgouchet.mesmaeker.core.ValidatingUtils.verifyFunctionThrows;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Xavier F. Gouchet
 */
public class ValidatingOngoingClause<T, O> implements OngoingClause<T, O> {

    private final Function<T, O> function;
    private final T subject;

    /**
     * @param instance
     * @param function
     */
    ValidatingOngoingClause(T instance, Function<T, O> function) {
        this.subject = instance;
        this.function = function;
    }

    @Override
    public OngoingClause<T, O> thenReturn(O value) {
        assertThat(function.apply(subject)).isEqualTo(value);
        return this;
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"}) @Override
    public OngoingClause<T, O> thenReturn(O initialValue, O... values) {
        assertThat(function.apply(subject)).isEqualTo(initialValue);

        for (O value : values) {
            assertThat(function.apply(subject)).isEqualTo(value);
        }
        return this;
    }

    @Override
    public OngoingClause<T, O> thenThrow(Throwable... throwables) {
        for (Throwable t : throwables) {
            verifyFunctionThrows(subject, function, t);
        }
        return this;
    }

    @Override
    public OngoingClause<T, O> thenThrow(Class<? extends Throwable> throwableType) {
        verifyFunctionThrows(subject, function, throwableType);
        return this;
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"}) @Override
    public OngoingClause<T, O> thenThrow(Class<? extends Throwable> toBeThrown, Class<? extends Throwable>... nextToBeThrown) {
        verifyFunctionThrows(subject, function, toBeThrown);

        for (Class<? extends Throwable> t : nextToBeThrown) {
            verifyFunctionThrows(subject, function, t);
        }
        return this;
    }
}
