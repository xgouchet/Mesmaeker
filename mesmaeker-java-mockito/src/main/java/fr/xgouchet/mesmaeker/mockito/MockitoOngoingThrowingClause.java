package fr.xgouchet.mesmaeker.mockito;

import org.mockito.Mockito;

import java.util.function.Consumer;

/**
 * @author Xavier F. Gouchet
 */
public class MockitoOngoingThrowingClause<T> implements OngoingThrowingClause<T> {

    private final T subject;
    private final Throwable[] expectedThrowables;


    public MockitoOngoingThrowingClause(T subject, Throwable[] toBeThrown) {
        this.subject = subject;
        expectedThrowables = toBeThrown;
    }

    @SuppressWarnings("unchecked")
    public MockitoOngoingThrowingClause(T subject, Class<? extends Throwable>... toBeThrown) {
        this.subject = subject;
        expectedThrowables = new Throwable[toBeThrown.length];

        for (int i = 0; i < toBeThrown.length; i++) {
            try {
                expectedThrowables[i] = toBeThrown[i].newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override public OngoingThrowingClause<T> when(Consumer<T> consumer) {
        T stubber = Mockito.doThrow(expectedThrowables).when(subject);
        consumer.accept(stubber);
        return this;
    }
}
