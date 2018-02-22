package fr.xgouchet.mesmaeker.sample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import fr.xgouchet.elmyr.junit.JUnitForger;
import fr.xgouchet.mesmaeker.annotations.Contract;
import fr.xgouchet.mesmaeker.core.BaseContractRule;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class StringDataWalkerTest {

    @Rule public MockitoRule mockito = MockitoJUnit.rule().silent();
    @Rule public BaseContractRule mockitoContract = new BaseContractRule();
    @Rule public JUnitForger forger = new JUnitForger();

    @Contract DataSourceContract contractDataSource;

    int fakeSize = 0;

    StringDataWalker testedWalker;

    @Before
    public void setUp() {
        fakeSize = forger.aTinyInt() + forger.aTinyInt();
        forger.setSeed(0x1f90f4a9);
        // System.out.println("Using contract " + contractDataSource + " / " + contractDataSource.getMock());
        testedWalker = new StringDataWalker(contractDataSource.getTypedMock());
    }

    @Test
    public void doubleBlind() {

    }

    //region walkToNext

    @Test
    public void walksToNextInTheMiddleOfTheDataSet() {
        // Given
        int position = forger.anInt(0, fakeSize - 2);
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(position + 1, fakeData);
        testedWalker.walkTo(position);

        // When
        testedWalker.walkForward();

        // Then
        assertThat(testedWalker.getCurrent()).isEqualTo(fakeData);
    }


    @Test
    public void walksToNextAtTheEndOfTheDataSetWithLoopEnabled() {
        // Given
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(0, fakeData);
        testedWalker.setLoopEnabled(true);
        testedWalker.walkTo(fakeSize - 1);

        // When
        testedWalker.walkForward();

        // Then
        assertThat(testedWalker.getCurrent()).isEqualTo(fakeData);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void walksToNextAtTheEndOfTheDataSetWithoutLoopEnabled() {
        // Given
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(0, fakeData);
        testedWalker.setLoopEnabled(false);
        testedWalker.walkTo(fakeSize - 1);

        // When
        testedWalker.walkForward();

        // Then
        testedWalker.getCurrent();
    }

    // endregion

    //region walkToPrevious

    @Test
    public void walksToPreviousInTheMiddleOfTheDataSet() {
        // Given
        int position = forger.anInt(1, fakeSize - 1);
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(position - 1, fakeData);
        testedWalker.walkTo(position);

        // When
        testedWalker.walkBackward();

        // Then
        assertThat(testedWalker.getCurrent()).isEqualTo(fakeData);
    }


    @Test
    public void walksToPreviousAtTheEndOfTheDataSetWithLoopEnabled() {
        // Given
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(fakeSize - 1, fakeData);
        testedWalker.setLoopEnabled(true);
        testedWalker.walkTo(0);

        // When
        testedWalker.walkBackward();

        // Then
        assertThat(testedWalker.getCurrent()).isEqualTo(fakeData);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void walksToPreviousAtTheEndOfTheDataSetWithoutLoopEnabled() {
        // Given
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(0, fakeData);
        testedWalker.setLoopEnabled(false);
        testedWalker.walkTo(0);

        // When
        testedWalker.walkBackward();

        // Then
        testedWalker.getCurrent();
    }

    // endregion

    // region Peeking

    @Test
    public void peeksNextInTheMiddleOfTheDataSet() {
        // Given
        int position = forger.anInt(0, fakeSize - 2);
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(position + 1, fakeData);

        // When
        testedWalker.walkTo(position);

        // Then
        assertThat(testedWalker.hasNext()).isTrue();
        assertThat(testedWalker.peekNext()).isEqualTo(fakeData);
    }


    @Test
    public void peeksNextAtTheEndOfTheDataSetWithLoopEnabled() {
        // Given
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(0, fakeData);
        testedWalker.setLoopEnabled(true);

        // When
        testedWalker.walkTo(fakeSize - 1);

        // Then
        assertThat(testedWalker.hasNext()).isTrue();
        assertThat(testedWalker.peekNext()).isEqualTo(fakeData);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void peeksNextAtTheEndOfTheDataSetWithoutLoopEnabled() {
        // Given
        String fakeData = forger.aString();
        contractDataSource.withSize(fakeSize);
        contractDataSource.withDataAt(0, fakeData);
        testedWalker.setLoopEnabled(false);

        // When
        testedWalker.walkTo(fakeSize - 1);

        // Then
        assertThat(testedWalker.hasNext()).isFalse();
        assertThat(testedWalker.peekNext()).isEqualTo(fakeData);
    }

    // endregion

}