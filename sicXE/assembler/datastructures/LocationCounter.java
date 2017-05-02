package sicXE.assembler.datastructures;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class LocationCounter {
    private int codeCounter;
    private int dataCounter;
    private int blockCounter;

    private Counter currentCounter = Counter.CODE;

    /**
     * increments the current location counter with the specified offset
     *
     * @param offset the offset to add to the current value of the current counter
     * @returns the new value of the current counter
     */
    public void increment(int offset) {
        switch (currentCounter) {
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

// --Commented out by Inspection START (5/1/17 3:49 AM):
//    public int setCurrentCounter(Counter c) {
//        currentCounter = c;
//        switch (currentCounter) {
//            case CODE:
//                return codeCounter;
//            case DATA:
//                return dataCounter;
//            case BLOCKS:
//                return blockCounter;
//            default:
//                return 0;
//        }
//    }
// --Commented out by Inspection STOP (5/1/17 3:49 AM)

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
