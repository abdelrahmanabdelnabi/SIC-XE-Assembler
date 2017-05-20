package src.filewriter;

/**
 * Created by abdelrahman on 5/20/17.
 */
public class TextRecord {
    private static final int MAX_RECORD_SIZE = 60;

    private int startAddress;

    private StringBuilder record;

    public TextRecord(int startAddress){
        this.startAddress = startAddress;
        record = new StringBuilder();
    }

    public void append(String objectCode) {
        if(! hasSpaceAvailable(objectCode.length()))
            throw new IllegalArgumentException("Text record does not have space available for: " +
                    objectCode);
        else
            record.append(objectCode);
    }

    public boolean hasSpaceAvailable(int length) {
        return MAX_RECORD_SIZE - (record.length() + length) >= 0;
    }

    public int getStartAddress(){
        return startAddress;
    }

    public int getLength() {
        return record.length()/2;
    }

    @Override
    public String toString() {
        if(record.length() == 0)
            return "";

        String length = String.format("%02X", record.length()/2);
        String address = String.format("%06X", startAddress);

        return "T" + address + length + record.toString() + "\n";
    }
}
