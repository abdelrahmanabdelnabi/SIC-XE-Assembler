package assembler.datastructures;

import java.util.Collections;
import java.util.Map;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class OpcodeTable {
    private static Map<String, InstProp> opCodeTable;

    public static Map<String, InstProp> getOpcodeTable() {
        fillOpcodeTable();
        return Collections.unmodifiableMap(opCodeTable);
    }

    public static int getOpcode(String instruction) {
        return opCodeTable.get(instruction).getOpCode();
    }

    public static Format getFormat(String instruction) {
        return opCodeTable.get(instruction).getFormat();
    }

    private static void fillOpcodeTable() {
        // TODO: insert instruction names and their properties
        // opCodeTable.put("", new InstProp());

    }
}
