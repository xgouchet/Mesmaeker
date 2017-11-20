package fr.xgouchet.mesmaeker.mockito;

import org.mockito.Mockito;

import java.util.function.Consumer;

/**
 * @author Xavier F. Gouchet
 */
public class MockitoOngoingVoidClause<T> implements OngoingVoidClause<T> {


    private final T subject;

    public MockitoOngoingVoidClause(T subject) {
        this.subject = subject;
    }

    @Override public OngoingVoidClause<T> when(Consumer<T> consumer) {
        T stubber = Mockito.doNothing().when(subject);
        consumer.accept(stubber);
        return this;
    }
}
