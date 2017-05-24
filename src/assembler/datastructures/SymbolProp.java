package src.assembler.datastructures;

/**
 * Created by abdelrahman on 4/8/17.
 */
public class SymbolProp {
    private final int address;
    private SymbolType type = SymbolType.NONE;

    public SymbolProp(int address) {
        this.address = address;
    }

    public SymbolProp(int address, SymbolType type) {
        this.address = address;
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }

    public enum SymbolType {
        RELATIVE, ABSOLUTE, EXTREF, NONE
    }
}
