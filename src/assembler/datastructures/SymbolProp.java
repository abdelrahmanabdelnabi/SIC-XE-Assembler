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

    public int getAddress() {
        return address;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }

    enum SymbolType {
        RELATIVE, ABSOLUTE, NONE
    }
}
