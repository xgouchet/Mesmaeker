package fr.xgouchet.mesmaeker.core


/**
 * @author Xavier F. Gouchet
 */
abstract class BaseContract<T> {

    private var instance: T? = null
    protected var isInstanceMock = true
        private set

    /**
     * @return the mock used by this contract. If the current instance is not a mock, an exception is thrown
     */
    fun getMock(): T {
        return if (isInstanceMock) {
            val msg = "This contract is using a null instance : make sure you initialize it using `withMock(…)`"
            instance ?: throw IllegalStateException(msg)
        } else {
            throw IllegalStateException("Contract is not currently using a mock")
        }
    }

    /**
     * @return the concrete instance used by this contract. If the current instance is a mock, an exception is thrown
     */
    fun getInstance(): T {
        return if (isInstanceMock) {
            throw IllegalStateException("Contract is currently using a mock")
        } else {
            val msg = "This contract is using a null instance : make sure you initialize it using `withInstance(…)`"
            instance ?: throw IllegalStateException(msg)
        }
    }

    /**
     * Make this contract use a mock instance.
     */
    fun withMock() {
        checkInstanceIsNull()
        instance = instantiateMock()
        isInstanceMock = true
    }

    /**
     * Make this contract use a concrete implementation instance.
     *
     * @param impl the implementation instance
     */
    fun withInstance(impl: T) {
        instance = impl
        isInstanceMock = false
    }

    /**
     * Applies a block of instruction on the instance, if the instance is a mock. If this contract
     * currently uses a concrete implementation instance, the block won't be used.
     *
     * @param block the block to apply
     */
    fun applyIfMock(block: (T) -> Unit) {
        if (isInstanceMock) {
            val currentInstance = instance
            if (currentInstance != null) {
                block(currentInstance)
            }
        }
    }

    /**
     * Applies a block of instruction on the instance, if the instance is a concrete implementation.
     * If this contract currently uses a mock instance, the block won't be used.
     *
     * @param block the block to apply
     */
    fun applyIfImplementation(block: (T) -> Unit) {
        if (!isInstanceMock) {
            val currentInstance = instance
            if (currentInstance != null) {
                block(currentInstance)
            }
        }
    }

    /**
     * @return a mock instance
     */
    abstract fun instantiateMock(): T


    /**
     * @return the parameters to use as input for the given clause to test, or null if the clause is
     * unknown or has no parmeters
     */
    abstract fun generateParameters(contractClause: String): Array<Any?>?


    private fun checkInstanceIsNull() {
        if (instance != null) {
            if (isInstanceMock) {
                throw IllegalStateException("This contract is already using a mock instance")
            } else {
                throw IllegalStateException("This contract is already using a real instance")
            }
        }
    }
}
