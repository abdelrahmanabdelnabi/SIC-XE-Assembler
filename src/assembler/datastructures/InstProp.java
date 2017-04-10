package src.assembler.datastructures;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class InstProp {

    private int opCode;
    private Format format;

    public InstProp(int opCode, Format format) {
        this.opCode = opCode;
        this.format = format;
    }

    public int getOpCode() {
        return opCode;
    }

    public Format getFormat() {
        return format;
    }
}
