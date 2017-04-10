package assembler.datastructures;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class LocationCounter {
    private int codeCounter;
    private int dataCounter;
    private int blockCounter;

    private Counter currentCounter;

    /**
     * increments the current location counter with the specified offset
     * @param offset the offset to add to the current value of the current counter
     *               @returns the new value of the current counter
     */
    public int increment(int offset) {
        switch (currentCounter) {
            case CODE:
                codeCounter += offset;
                return codeCounter;
            case DATA:
                dataCounter += offset;
                return dataCounter;
            case BLOCKS:
                blockCounter += offset;
                return blockCounter;
            default:
                return 0;
        }
    }

    public int setCurrentCounter(Counter c) {
        currentCounter = c;
        switch (currentCounter) {
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

    public int getCurrentCounterValue() {
        switch (currentCounter) {
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
        switch (currentCounter) {
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

    public enum Counter {
        CODE, DATA, BLOCKS
    }
}
