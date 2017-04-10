package src.assembler.datastructures;

import java.util.*;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class OpcodeTable {
    private static Map<String, InstProp> opCodeTable = new HashMap<>();
    private static Set<String> assemblerDirectives = new HashSet<>();

    // static initialization block to fill the tables before they are used anywhere in the program
    static {
        fillOpcodeTable();
        // TODO: call a method that fills the assembler directives table
    }

    public static Set<String> getAssemblerDirectivesTable() {
        return Collections.unmodifiableSet(assemblerDirectives);
    }

    public static Map<String, InstProp> getOpcodeTable() {
        return Collections.unmodifiableMap(opCodeTable);
    }

    public static boolean isMnemonic(String mnemonic) {
        return isDirective(mnemonic) || isOpcode(mnemonic);
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
        opCodeTable.put("ADD", new InstProp(24, Format.FORMAT3_4));
        opCodeTable.put("ADDF", new InstProp(88, Format.FORMAT3_4));
        opCodeTable.put("ADDR", new InstProp(144, Format.FORMAT2));
        opCodeTable.put("AND", new InstProp(64, Format.FORMAT3_4));
        opCodeTable.put("CLEAR", new InstProp(180, Format.FORMAT2));
        opCodeTable.put("COMP", new InstProp(40, Format.FORMAT3_4));
        opCodeTable.put("COMPF", new InstProp(136, Format.FORMAT3_4));
        opCodeTable.put("COMPR", new InstProp(160, Format.FORMAT2));
        opCodeTable.put("DIV", new InstProp(36, Format.FORMAT3_4));
        opCodeTable.put("DIVF", new InstProp(100, Format.FORMAT3_4));
        opCodeTable.put("DIVR", new InstProp(156, Format.FORMAT2));
        opCodeTable.put("FIX", new InstProp(196, Format.FORMAT1));
        opCodeTable.put("FLOAT", new InstProp(192, Format.FORMAT1));
        opCodeTable.put("HIO", new InstProp(244, Format.FORMAT1));
        opCodeTable.put("J", new InstProp(60, Format.FORMAT3_4));
        opCodeTable.put("JEQ", new InstProp(48, Format.FORMAT3_4));
        opCodeTable.put("JGT", new InstProp(52, Format.FORMAT3_4));
        opCodeTable.put("JLT", new InstProp(56, Format.FORMAT3_4));
        opCodeTable.put("JSUB", new InstProp(72, Format.FORMAT3_4));
        opCodeTable.put("LDA", new InstProp(0, Format.FORMAT3_4));
        opCodeTable.put("LDB", new InstProp(104, Format.FORMAT3_4));
        opCodeTable.put("LDCH", new InstProp(80, Format.FORMAT3_4));
        opCodeTable.put("LDF", new InstProp(112, Format.FORMAT3_4));
        opCodeTable.put("LDL", new InstProp(8, Format.FORMAT3_4));
        opCodeTable.put("LDS", new InstProp(108, Format.FORMAT3_4));
        opCodeTable.put("LDT", new InstProp(116, Format.FORMAT3_4));
        opCodeTable.put("LDX", new InstProp(4, Format.FORMAT3_4));
        opCodeTable.put("LPS", new InstProp(208, Format.FORMAT3_4));
        opCodeTable.put("MUL", new InstProp(32, Format.FORMAT3_4));
        opCodeTable.put("MULF", new InstProp(96, Format.FORMAT3_4));
        opCodeTable.put("MULR", new InstProp(152, Format.FORMAT2));
        opCodeTable.put("NORM", new InstProp(200, Format.FORMAT1));
        opCodeTable.put("OR", new InstProp(68, Format.FORMAT3_4));
        opCodeTable.put("RD", new InstProp(216, Format.FORMAT3_4));
        opCodeTable.put("RMO", new InstProp(172, Format.FORMAT2));
        opCodeTable.put("RSUB", new InstProp(76, Format.FORMAT3_4));
        opCodeTable.put("SHIFTL", new InstProp(164, Format.FORMAT2));
        opCodeTable.put("SHIFTR", new InstProp(168, Format.FORMAT2));
        opCodeTable.put("SIO", new InstProp(240, Format.FORMAT1));
        opCodeTable.put("SUBR", new InstProp(148, Format.FORMAT2));
        opCodeTable.put("SVC", new InstProp(176, Format.FORMAT2));
        opCodeTable.put("TD", new InstProp(224, Format.FORMAT3_4));
        opCodeTable.put("TIO", new InstProp(248, Format.FORMAT1));
        opCodeTable.put("TIX", new InstProp(44, Format.FORMAT3_4));
        opCodeTable.put("TIXR", new InstProp(184, Format.FORMAT2));
        opCodeTable.put("WD", new InstProp(220, Format.FORMAT3_4));
        opCodeTable.put("SSK", new InstProp(236, Format.FORMAT3_4));
        opCodeTable.put("STA", new InstProp(12, Format.FORMAT3_4));
        opCodeTable.put("STB", new InstProp(120, Format.FORMAT3_4));
        opCodeTable.put("STCH", new InstProp(84, Format.FORMAT3_4));
        opCodeTable.put("STF", new InstProp(128, Format.FORMAT3_4));
        opCodeTable.put("STI", new InstProp(212, Format.FORMAT3_4));
        opCodeTable.put("STL", new InstProp(20, Format.FORMAT3_4));
        opCodeTable.put("STS", new InstProp(124, Format.FORMAT3_4));
        opCodeTable.put("STSW", new InstProp(232, Format.FORMAT3_4));
        opCodeTable.put("STT", new InstProp(132, Format.FORMAT3_4));
        opCodeTable.put("STX", new InstProp(16, Format.FORMAT3_4));
        opCodeTable.put("SUB", new InstProp(28, Format.FORMAT3_4));
        opCodeTable.put("SUBF", new InstProp(92, Format.FORMAT3_4));
    }
}
