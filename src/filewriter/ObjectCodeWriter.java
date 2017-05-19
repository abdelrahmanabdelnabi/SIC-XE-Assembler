package src.filewriter;

/**
 * Created by abdelrahman on 5/19/17.
 */
public class ObjectCodeWriter {



    private int currentRecordAddress = 0;

    private static final int MAX_RECORD_SIZE = 30;

    // modification record signs
    public static final boolean PLUS = true;
    public static final boolean MINUS = false;

    public ObjectCodeWriter(String programName, int startAddress, int length) {
        this.currentRecordAddress = startAddress;
    }

    /**
     * appends the object code passed to the method to current open text record
     *
     * @param newCode the object code to be appended
     */
    public void appendTextRecord(String newCode) {

    }

    /**
     * end the current text record and starts a new text record with the specified address
     *
     * @param newAddress the starting address of the new text record
     */
    public void startNewTextRecord(int newAddress) {
    }

    /**
     * add a new modification record to the generated object code
     * modification records appear in the object code in the same order they are added with calls to
     * this method.
     *
     * @param startAddress the starting address of the address to be modified
     * @param length the length of the address to be modified
     * @param sign is the modification addition or subtraction
     * @param label external symbol whose value is to be added or subtracted from the indicated
     *              field
     */
    public void addModificationRecord(int startAddress, int length, boolean sign, String label) {

    }

    /**
     * appends a Refer record to the gererated object code.
     * referred symbols appear in the object code in the same order they are added with calls to
     * this method.
     *
     * @param symbol Name of external symbol to be referred
     */
    public void addReferRecord(String symbol) {

    }

    /**
     * appends a Define record to the generated object code.
     * Defined symbols appear in the object code in the same order they are added with calls to
     * this method.
     *
     * @param symbol Name of external symbol to be defined
     * @param address relative address of symbol (within this control section)
     */
    public void appendDefineRecord(String symbol, int address) {

    }

    /**
     * returns all the object code added thus far in this order: Head, Define, Refer, Text,
     * Modification, End
     *
     * @return all the added records formatted properly
     */
    public String getObjectCode() {
        return "";
    }

    private String getHeadRecord(String programName, int startAddress, int length) {
        return "";
    }

    private void finishCurrentTextRecord() {

    }
}
