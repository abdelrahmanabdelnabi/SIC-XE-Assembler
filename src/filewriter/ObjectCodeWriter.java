package src.filewriter;

/**
 * Created by abdelrahman on 5/19/17.
 */
public class ObjectCodeWriter {

    private StringBuilder objectCode;
    private StringBuilder defineRecords;
    private StringBuilder modificationRecords;
    private StringBuilder referRecords;

    int startAddress;

    public ObjectCodeWriter(String programName, int startAddress, int length) {
        objectCode = new StringBuilder();
        defineRecords = new StringBuilder();
        modificationRecords = new StringBuilder();
        referRecords = new StringBuilder();
        this.startAddress = startAddress;
        objectCode.append(getHeadRecord(programName, startAddress, length));
    }

    public void appendTextRecord(String tRecord) {

    }

    public void startNewTextRecord(int newAddress) {

    }

    public void addModificationRecord(int startAddress, int length, boolean sign, String label) {

    }

    public void addReferRecord(String ) {

    }

    public void appendDefineRecord() {

    }

    private String getHeadRecord(String programName, int startAddress, int length) {
        return "";
    }

    private void finishCurrentTextRecord() {

    }

    public String getObjectCode() {

    }
}
