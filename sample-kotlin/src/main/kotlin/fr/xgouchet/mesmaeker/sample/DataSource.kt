package fr.xgouchet.mesmaeker.sample

interface DataSource {

    fun getDataSet(): Int

    fun getDataAt(position: Int): String

    fun size(): Int
}
