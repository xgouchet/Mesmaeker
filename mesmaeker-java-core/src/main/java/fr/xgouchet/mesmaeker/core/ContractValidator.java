package fr.xgouchet.mesmaeker.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xavier F. Gouchet
 */
@RunWith(Parameterized.class)
public abstract class ContractValidator<T, C extends Contract<T>> {

    public static <T, C extends Contract<T>> List<Object[]> generateParameters(Class<C> contractClass) {
        Method[] declaredMethods = contractClass.getDeclaredMethods();


        return Arrays.stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(Clause.class))
                .filter(method -> method.getParameterTypes().length == 0)
                .filter(method -> method.getReturnType() == Void.TYPE)
                .map(method -> new Object[]{method.getName()})
                .collect(Collectors.toList());
    }

    private final String contractClause;

    protected ContractValidator(String contractClause) {
        this.contractClause = contractClause;
    }

    @Test
    public void validateClause() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        C contract = instantiateContract();
        T subject = instantiateSubject();

        contract.setImplementationInstance(subject);

        Method method = contract.getClass().getDeclaredMethod(contractClause);

        method.invoke(contract);
    }

    protected abstract C instantiateContract();

    protected abstract T instantiateSubject();
}
