package fr.xgouchet.mesmaeker.sample

interface DataWalker<T> {

    fun getCurrent(): T

    fun walkForward()

    fun walkBackward()

    fun walkTo(position: Int)

    fun hasNext(): Boolean

    fun peekNext(): T

    fun setLoopEnabled(enabled: Boolean)

}
