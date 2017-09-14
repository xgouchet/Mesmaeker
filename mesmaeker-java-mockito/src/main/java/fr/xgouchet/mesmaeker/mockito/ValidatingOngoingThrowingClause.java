package fr.xgouchet.mesmaeker.mockito;

import java.util.function.Consumer;

import static fr.xgouchet.mesmaeker.core.ValidatingUtils.verifyConsumerThrows;

/**
 * @author Xavier F. Gouchet
 */
public class ValidatingOngoingThrowingClause<T> implements OngoingThrowingClause<T> {


    private final T subject;
    private final Class<? extends Throwable>[] expectedThrowables;


    public ValidatingOngoingThrowingClause(T subject, Class<? extends Throwable>... toBeThrown) {
        this.subject = subject;
        expectedThrowables = toBeThrown;
    }

    public ValidatingOngoingThrowingClause(T subject, Throwable... toBeThrown) {
        this.subject = subject;
        //noinspection unchecked
        expectedThrowables = new Class[toBeThrown.length];

        for (int i = 0; i < toBeThrown.length; i++) {
            expectedThrowables[i] = toBeThrown[i].getClass();
        }
    }

    @Override public OngoingThrowingClause<T> when(Consumer<T> consumer) {

        for (Class<? extends Throwable> t : expectedThrowables) {
            verifyConsumerThrows(subject, consumer, t);
        }
        return this;
    }
}
