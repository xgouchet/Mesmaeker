package fr.xgouchet.mesmaeker.core

import fr.xgouchet.mesmaeker.annotations.Contract
import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement
import java.lang.reflect.Field

class BaseContractRule : MethodRule {

    override fun apply(base: Statement,
                       method: FrameworkMethod,
                       target: Any)
            : Statement {
        return object : Statement() {
            override fun evaluate() {
                process(target, target.javaClass)
                base.evaluate()
            }
        }
    }

    protected fun process(testInstance: Any, testClass: Class<*>) {
        val fields = testClass.declaredFields
        for (field in fields) {
            processField(testInstance, field)
        }
    }

    private fun processField(testInstance: Any, field: Field) {
        for (annotation in field.annotations) {
            if (annotation is Contract) {
                val contractClass = field.type
                if (!BaseContract::class.java.isAssignableFrom(contractClass)) {
                    throw IllegalStateException("Annotation @Contract should be used on a child inheriting from BaseContract. Field ${field.name} in ${testInstance.javaClass.simpleName} doesn't match this requirement.")
                }

                @Suppress("UNCHECKED_CAST")
                assignNewContract(testInstance, contractClass as Class<BaseContract<Any>>, field)
            }
        }
    }

    private fun <X, F : BaseContract<X>> assignNewContract(testInstance: Any, fieldClass: Class<F>, field: Field) {
        val contract: F = try {
            fieldClass.newInstance()
        } catch (e: IllegalAccessException) {
            throw IllegalStateException("Unable to instantiate a contract for field ${field.name} in ${testInstance.javaClass.simpleName}. Make sure the contract class is public.", e)
        } catch (e: InstantiationException) {
            throw IllegalStateException("Unable to instantiate a contract for field ${field.name} in ${testInstance.javaClass.simpleName}. Make sure the contract class is not abstract, and it has a default constructor.", e)
        } finally {

        }
        contract.withMock()
        val wasAccessible = field.isAccessible
        try {
            if (!wasAccessible) {
                field.isAccessible = true
            }
            field.set(testInstance, contract)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } finally {
            if (!wasAccessible) {
                field.isAccessible = false
            }
        }
    }
}

