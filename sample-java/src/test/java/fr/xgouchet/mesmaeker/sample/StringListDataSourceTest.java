package fr.xgouchet.mesmaeker.sample;

import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collection;

import fr.xgouchet.mesmaeker.core.ContractValidator;

public class StringListDataSourceTest extends ContractValidator<StringListDataSource, DataSourceContract<StringListDataSource>> {


    @SuppressWarnings("unchecked")
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return generateParameters(DataSourceContract.class);
    }

    public StringListDataSourceTest(@NotNull String contractClause) {
        super(contractClause);
    }

    @NotNull
    @Override
    protected DataSourceContract<StringListDataSource> instantiateContract() {
        return new DataSourceContract<StringListDataSource>() {
            @Override
            void initWithSize(StringListDataSource dataSource, int size) {
                dataSource.ensureSize(size, null);
            }

            @Override
            void putData(StringListDataSource dataSource, int position, String data) {
                dataSource.ensureSize(position + 1, null);
                dataSource.set(position, data);
            }
        };
    }

    @Override
    protected StringListDataSource instantiateSubject() {
        return new StringListDataSource();
    }


}