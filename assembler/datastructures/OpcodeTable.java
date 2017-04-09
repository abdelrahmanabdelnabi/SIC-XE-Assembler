package assembler.datastructures;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class OpcodeTable {
    private static Map<String, InstProp> opCodeTable;
    private static Set<String> assemblerDirectives;

    public static Map<String, InstProp> getOpcodeTable() {
        fillOpcodeTable();
        return Collections.unmodifiableMap(opCodeTable);
    }

    public static boolean isDirective(String directive) {
        return assemblerDirectives.contains(directive);
    }

    public static boolean isOpcode(String opcode) {
        return opCodeTable.containsKey(opcode);
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
