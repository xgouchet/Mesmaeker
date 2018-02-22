package fr.xgouchet.mesmaeker.sample

import fr.xgouchet.elmyr.junit.JUnitForger
import fr.xgouchet.mesmaeker.annotations.Contract
import fr.xgouchet.mesmaeker.core.BaseContractRule
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

class StringDataWalkerTest {

    @Rule var mockito = MockitoJUnit.rule().silent()
    @Rule var mockitoContract = BaseContractRule()
    @Rule var forger = JUnitForger()

    @Contract lateinit var contractDataSource: DataSourceContract<*>

    var fakeSize = 0

    lateinit var testedWalker: StringDataWalker

    @Before
    fun setUp() {
        fakeSize = forger.aTinyInt() + forger.aTinyInt()
        forger.seed = 0x1f90f4a9
        // System.out.println("Using contract " + contractDataSource + " / " + contractDataSource.getMock());
        testedWalker = StringDataWalker(contractDataSource!!.typedMock)
    }

    @Test
    fun doubleBlind() {

    }

    //region walkToNext

    @Test
    fun walksToNextInTheMiddleOfTheDataSet() {
        // Given
        val position = forger.anInt(0, fakeSize - 2)
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(position + 1, fakeData)
        testedWalker.walkTo(position)

        // When
        testedWalker.walkForward()

        // Then
        assertThat(testedWalker.current).isEqualTo(fakeData)
    }


    @Test
    fun walksToNextAtTheEndOfTheDataSetWithLoopEnabled() {
        // Given
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(0, fakeData)
        testedWalker.setLoopEnabled(true)
        testedWalker.walkTo(fakeSize - 1)

        // When
        testedWalker.walkForward()

        // Then
        assertThat(testedWalker.current).isEqualTo(fakeData)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun walksToNextAtTheEndOfTheDataSetWithoutLoopEnabled() {
        // Given
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(0, fakeData)
        testedWalker.setLoopEnabled(false)
        testedWalker.walkTo(fakeSize - 1)

        // When
        testedWalker.walkForward()

        // Then
        testedWalker.current
    }

    // endregion

    //region walkToPrevious

    @Test
    fun walksToPreviousInTheMiddleOfTheDataSet() {
        // Given
        val position = forger.anInt(1, fakeSize - 1)
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(position - 1, fakeData)
        testedWalker.walkTo(position)

        // When
        testedWalker.walkBackward()

        // Then
        assertThat(testedWalker.current).isEqualTo(fakeData)
    }


    @Test
    fun walksToPreviousAtTheEndOfTheDataSetWithLoopEnabled() {
        // Given
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(fakeSize - 1, fakeData)
        testedWalker.setLoopEnabled(true)
        testedWalker.walkTo(0)

        // When
        testedWalker.walkBackward()

        // Then
        assertThat(testedWalker.current).isEqualTo(fakeData)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun walksToPreviousAtTheEndOfTheDataSetWithoutLoopEnabled() {
        // Given
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(0, fakeData)
        testedWalker.setLoopEnabled(false)
        testedWalker.walkTo(0)

        // When
        testedWalker.walkBackward()

        // Then
        testedWalker.current
    }

    // endregion

    // region Peeking

    @Test
    fun peeksNextInTheMiddleOfTheDataSet() {
        // Given
        val position = forger.anInt(0, fakeSize - 2)
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(position + 1, fakeData)

        // When
        testedWalker.walkTo(position)

        // Then
        assertThat(testedWalker.hasNext()).isTrue()
        assertThat(testedWalker.peekNext()).isEqualTo(fakeData)
    }


    @Test
    fun peeksNextAtTheEndOfTheDataSetWithLoopEnabled() {
        // Given
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(0, fakeData)
        testedWalker.setLoopEnabled(true)

        // When
        testedWalker.walkTo(fakeSize - 1)

        // Then
        assertThat(testedWalker.hasNext()).isTrue()
        assertThat(testedWalker.peekNext()).isEqualTo(fakeData)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun peeksNextAtTheEndOfTheDataSetWithoutLoopEnabled() {
        // Given
        val fakeData = forger.aString()
        contractDataSource!!.withSize(fakeSize)
        contractDataSource!!.withDataAt(0, fakeData)
        testedWalker.setLoopEnabled(false)

        // When
        testedWalker.walkTo(fakeSize - 1)

        // Then
        assertThat(testedWalker.hasNext()).isFalse()
        assertThat(testedWalker.peekNext()).isEqualTo(fakeData)
    }

    // endregion

}