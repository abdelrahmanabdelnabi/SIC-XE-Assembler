package src.filewriter;

import java.util.ArrayList;

/**
 * Created by abdelrahman on 5/19/17.
 */
public class ObjectCodeWriter {

    private ArrayList<String> textRecords;
    private StringBuilder dRecords;
    private StringBuilder rRecords;
    private StringBuilder mRecords;

    private TextRecord currentRecord;

    // modification record signs
    public static final boolean PLUS = true;
    public static final boolean MINUS = false;
    private String headRecord;
    private String endRecord;

    public ObjectCodeWriter(String programName, int startAddress, int length) {
        currentRecord = new TextRecord(startAddress);
        textRecords = new ArrayList<>();
        mRecords = new StringBuilder();
        rRecords = new StringBuilder();
        dRecords = new StringBuilder();

        headRecord = getHeadRecord(programName, startAddress, length);
        endRecord = getEndRecord(startAddress);
    }

    /**
     * appends the object code passed to the method to current open text record
     *
     * @param newCode the object code to be appended
     */
    public void appendTextRecord(String newCode) {
        if(currentRecord.hasSpaceAvailable(newCode.length()))
            currentRecord.append(newCode);
        else {
            textRecords.add(currentRecord.toString());
            int nextRecordAddress = currentRecord.getStartAddress() + currentRecord.getLength();
            currentRecord = new TextRecord(nextRecordAddress);
        }

    }

    /**
     * end the current text record and starts a new text record with the specified address
     *
     * @param newAddress the starting address of the new text record
     */
    public void startNewTextRecord(int newAddress) {

        String r = currentRecord.toString();
        if(!r.isEmpty())
            textRecords.add(r);

        currentRecord = new TextRecord(newAddress);
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
        String s = "M" + String.format("%06X%02X%s%6s", startAddress, length, sign == PLUS ? "+" :
                        "-", label);
        mRecords.append(s);
    }

    /**
     * appends a Refer record to the gererated object code.
     * referred symbols appear in the object code in the same order they are added with calls to
     * this method.
     *
     * @param symbol Name of external symbol to be referred
     */
    public void appendReferRecord(String symbol) {
        String s = String.format("%6s", symbol);
        rRecords.append(s);
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
        String s = String.format("%6s%6s", symbol, address);
        dRecords.append(s);
    }

    /**
     * returns all the object code added thus far in this order: Head, Define, Refer, Text,
     * Modification, End
     *
     * @return all the added records formatted properly
     */
    public String getObjectCode() {
        StringBuilder objectCode = new StringBuilder();
        objectCode.append(headRecord);
        objectCode.append(dRecords.toString());
        objectCode.append(rRecords.toString());

        for(String s : textRecords)
            objectCode.append(s);

        objectCode.append(endRecord);
        return "";
    }

    private String getHeadRecord(String programName, int startAddress, int length) {
        return "H" + String.format("%6s%06X%06X", programName, startAddress, length);
    }

    private String getEndRecord(int progStartAddress) {
        return "E" + String.format("%6s", progStartAddress);
    }

}
