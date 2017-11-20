package fr.xgouchet.mesmaeker.mockito;

import org.mockito.Mockito;

import java.util.function.Function;

import fr.xgouchet.mesmaeker.core.Contract;


/**
 * @author Xavier F. Gouchet
 */
public class MockitoContract<T> extends Contract<T> {

    public void useMockInstance(Class<T> tClass) {
        setMockInstance(Mockito.mock(tClass));
    }

    public <O> OngoingClause<T, O> whenever(Function<T, O> function) {
        if (isInstanceMock()) {
            return new MockitoOngoingClause<>(getInstance(), function);
        } else {
            return new ValidatingOngoingClause<>(getInstance(), function);
        }
    }

    public <O> OngoingClause<T, O> when(Function<T, O> function) {
        if (isInstanceMock()) {
            return new MockitoOngoingClause<>(getInstance(), function);
        } else {
            return new ValidatingOngoingClause<>(getInstance(), function);
        }
    }

    public OngoingThrowingClause<T> doThrow(Throwable... throwables) {
        if (isInstanceMock()) {
            return new MockitoOngoingThrowingClause<>(getMock(), throwables);
        } else {
            return new ValidatingOngoingThrowingClause<>(getInstance(), throwables);
        }
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"})
    public OngoingThrowingClause<T> doThrow(Class<? extends Throwable>... throwableClasses) {
        if (isInstanceMock()) {
            return new MockitoOngoingThrowingClause<>(getMock(), throwableClasses);
        } else {
            return new ValidatingOngoingThrowingClause<>(getInstance(), throwableClasses);
        }
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"})
    public <O> OngoingReturningClause<T, O> doReturn(O value, O... values) {
        if (isInstanceMock()) {
            return new MockitoOngoingReturningClause<>(getInstance(), value, values);
        } else {
            return new ValidatingOngoingReturningClause<>(getInstance(), value, values);
        }
    }

    public OngoingVoidClause<T> doNothing() {
        if (isInstanceMock()) {
            return new MockitoOngoingVoidClause<>(getMock());
        } else {
            return new ValidatingOngoingVoidClause<>(getInstance());
        }
    }


    // TODO public static <O> OngoingThrowingClause<T, O> doAnswer(Answer answer) {    }
}
