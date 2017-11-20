package fr.xgouchet.mesmaeker.mockito;

import fr.xgouchet.mesmaeker.core.ContractValidator;
import org.junit.runners.Parameterized;

import java.util.Collection;

/**
 * @author Xavier F. Gouchet
 */
public class FooTest extends ContractValidator<Foo, FooContract> {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return generateParameters(FooContract.class);
    }

    public FooTest(String contractClause) {
        super(contractClause);
    }

    @Override protected FooContract instantiateContract() {
        return new FooContract();
    }

    @Override protected Foo instantiateSubject() {
        return new Foo();
    }
}
