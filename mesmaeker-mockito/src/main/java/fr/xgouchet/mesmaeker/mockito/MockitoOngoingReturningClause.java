package fr.xgouchet.mesmaeker.mockito;

import org.mockito.Mockito;

import java.util.function.Function;

/**
 * @author Xavier F. Gouchet
 */
public class MockitoOngoingReturningClause<T, O> implements OngoingReturningClause<T, O> {

    private final T subject;
    private final O firstResult;
    private final O[] nextResults;

    @SuppressWarnings("unchecked")
    public MockitoOngoingReturningClause(T subject, O toBeReturned, O... toBeReturnedNext) {
        this.subject = subject;
        firstResult = toBeReturned;
        nextResults = toBeReturnedNext;
    }

    @Override
    public OngoingReturningClause<T, O> when(Function<T, O> function) {
        T stubber;
        if ((nextResults == null) || (nextResults.length == 0)) {
            stubber = Mockito.doReturn(firstResult).when(subject);
        } else {
            stubber = Mockito.doReturn(firstResult, (Object[]) nextResults).when(subject);
        }
        function.apply(stubber);
        return this;
    }
}
