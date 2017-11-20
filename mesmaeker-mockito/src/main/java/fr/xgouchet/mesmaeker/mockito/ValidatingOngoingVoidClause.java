package fr.xgouchet.mesmaeker.mockito;

import java.util.function.Consumer;

/**
 * @author Xavier F. Gouchet
 */
public class ValidatingOngoingVoidClause<T> implements OngoingVoidClause<T> {

    private final T subject;

    public ValidatingOngoingVoidClause(T subject) {
        this.subject = subject;
    }

    @Override public OngoingVoidClause<T> when(Consumer<T> consumer) {
        consumer.accept(subject);
        return this;
    }
}
