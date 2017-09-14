package fr.xgouchet.mesmaeker.core;

import java.util.function.Consumer;

/**
 * @author Xavier F. Gouchet
 */
public class Contract<T> {

    private T instance = null;
    private boolean instanceIsMock = true;

    protected boolean isInstanceMock() {
        return instanceIsMock;
    }

    protected T getInstance() {
        return instance;
    }

    public T getMock(){
        if (instanceIsMock){
            return instance;
        } else {
            throw new IllegalStateException("Contract is not currently using a mock");
        }
    }

    /**
     * Make this contract use a mock instance.
     *
     * @param mock the mock instance
     */
    public void setMockInstance(T mock) {
        instance = mock;
        instanceIsMock = true;
    }

    /**
     * Make this contract use a concrete implementation instance.
     *
     * @param impl the implementation instance
     */
    public void setImplementationInstance(T impl) {
        instance = impl;
        instanceIsMock = false;
    }

    /**
     * Applies a block of instruction on the instance, if the instance is a mock. If this contract currently uses a
     * concrete implementation instance, the block won't be used.
     *
     * @param consumer the block to apply
     */
    public void applyIfMock(Consumer<T> consumer) {
        if (instanceIsMock) {
            if (instance != null) {
                consumer.accept(instance);
            }
        }
    }

    /**
     * Applies a block of instruction on the instance, if the instance is a concrete implementation. If this contract
     * currently uses a mock instance, the block won't be used.
     *
     * @param consumer the block to apply
     */
    public void applyIfImplementation(Consumer<T> consumer) {
        if (!instanceIsMock) {
            if (instance != null) {
                consumer.accept(instance);
            }
        }
    }

}
