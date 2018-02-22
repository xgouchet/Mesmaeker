package fr.xgouchet.mesmaeker.core

import fr.xgouchet.mesmaeker.annotations.Clause
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.lang.reflect.InvocationTargetException

/**
 * @author Xavier F. Gouchet
 */
@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
@RunWith(Parameterized::class)
abstract class ContractValidator<T, out C : BaseContract<T>>
protected constructor(private val contractClause: String) {

    @Test
    @Throws(InvocationTargetException::class, IllegalAccessException::class, NoSuchMethodException::class)
    fun validateClause() {
        val contract = instantiateContract()
        val subject = instantiateSubject()

        contract.withInstance(subject)

        val parameters = contract.generateParameters(contractClause) ?: emptyArray()
        val method = contract.javaClass.methods.firstOrNull {
            (it.name == contractClause) && parametersMatch(parameters, it.parameterTypes)
        }

        if (method == null) {
            throw IllegalStateException("Couldn't match claus $contractClause with the given parameters")
        } else {
            method.invoke(contract, *parameters)
        }
    }

    private fun parametersMatch(parameters: Array<Any?>, parameterTypes: Array<out Class<*>>): Boolean {
        if (parameters.size != parameterTypes.size) {
            return false
        }

        for (i in 0 until parameters.size) {
            val param = parameters[i] ?: continue
            val expectedType = parameterTypes[i]

            val matches = when (expectedType) {
                Integer.TYPE -> (expectedType == Integer.TYPE) || (expectedType == Integer::class.java)
                else -> expectedType.isAssignableFrom(param.javaClass)
            }

            if (!matches) return false
        }

        return true
    }

    protected abstract fun instantiateContract(): C

    protected abstract fun instantiateSubject(): T

    companion object {

        @JvmStatic
        fun <T, C : BaseContract<T>> generateParameters(contractClass: Class<C>): List<Array<Any>> {
            val declaredMethods = contractClass.declaredMethods

            val annotatedMethods = declaredMethods
                    .filter { method -> method.isAnnotationPresent(Clause::class.java) }

            val firstNonVoid = annotatedMethods.firstOrNull { method -> method.returnType != Void.TYPE }
            if (firstNonVoid != null) {
                throw IllegalStateException("Methods annotated with @Clause must return void! Method ${firstNonVoid.name} in ${contractClass.simpleName} doesn't match this requirement.")
            }

            return annotatedMethods
                    .filter { method -> method.returnType == Void.TYPE }
                    .map { method -> arrayOf<Any>(method.name) }
                    .toList()
        }
    }
}
