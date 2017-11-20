package fr.xgouchet.mesmaeker.mockito;

import java.util.function.Function;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Xavier F. Gouchet
 */
public class ValidatingOngoingReturningClause<T, O> implements OngoingReturningClause<T, O> {

    private final T subject;
    private final O firstResult;
    private final O[] nextResults;

    @SuppressWarnings("unchecked")
    public ValidatingOngoingReturningClause(T subject, O firstResult, O... nextResults) {
        this.subject = subject;
        this.firstResult = firstResult;
        this.nextResults = nextResults;
    }

    @Override
    public OngoingReturningClause<T, O> when(Function<T, O> function) {
        assertThat(function.apply(subject)).isEqualTo(firstResult);

        if (nextResults != null) {
            for (O nextResult : nextResults) {
                assertThat(function.apply(subject)).isEqualTo(nextResult);
            }
        }
        return this;
    }
}
