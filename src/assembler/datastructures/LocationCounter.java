package src.assembler.datastructures;

import static src.assembler.datastructures.LocationCounter.Counter.*;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class LocationCounter {
    private int codeCounter;
    private int dataCounter;
    private int blockCounter;
    private int cachedCounter;

    private Counter cachedType = CODE;
    private Counter counterType = CODE;

    /**
     * increments the current location counter with the specified offset
     *
     * @param offset the offset to add to the current value of the current counter
     * @returns the new value of the current counter
     */
    public void increment(int offset) {
        switch (counterType) {
            case CODE:
                codeCounter += offset;
                return;
            case DATA:
                dataCounter += offset;
                return;
            case BLOCKS:
                blockCounter += offset;
                return;
            default:
        }
    }

    public int getCurrentCounterValue() {
        switch (counterType) {
            case CODE:
                return codeCounter;
            case DATA:
                return dataCounter;
            case BLOCKS:
                return blockCounter;
            default:
                return 0;
        }
    }

    public void setCurrentCounterValue(int newValue) {
        switch (counterType) {
            case CODE:
                codeCounter = newValue;
                break;
            case DATA:
                dataCounter = newValue;
                break;
            case BLOCKS:
                blockCounter = newValue;
                break;
        }
    }

    public void orgSet(int newValue) {
        switch (counterType) {
            case DATA:
                cachedCounter = dataCounter;
                cachedType = DATA;
                dataCounter = newValue;
                break;
            case CODE:
                cachedCounter = codeCounter;
                cachedType = CODE;
                codeCounter = newValue;
                break;
            case BLOCKS:
                cachedCounter = blockCounter;
                cachedType = BLOCKS;
                blockCounter = cachedCounter;
                break;
        }
    }


    public void orgRestore() {
        switch (cachedType) {
            case DATA:
                dataCounter = cachedCounter;
                break;
            case CODE:
                codeCounter = cachedCounter;
                break;
            case BLOCKS:
                blockCounter = cachedCounter;
                break;
        }
    }

    public enum Counter {
        CODE, DATA, BLOCKS
    }
}
