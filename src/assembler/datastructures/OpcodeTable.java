package src.assembler.datastructures;

import java.util.*;

import static src.assembler.datastructures.OperandType.*;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class OpcodeTable {
    private static Map<String, InstProp> opCodeTable = new HashMap<>();
    private static Set<String> assemblerDirectives = new HashSet<>();
    private static String programName = "";
    private static int startAddress = 0;
    private static int programLength;

    // static initialization block to fill the tables before they are used anywhere in the program
    static {
        fillOpcodeTable();
        fillAssemblerDirectives();
    }

    public static Set<String> getAssemblerDirectivesSet() {
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

    public static int getOpCode(String mnemonic) {
        return opCodeTable.get(mnemonic).getOpCode();
    }

    public static Format getFormat(String instruction) {
        return opCodeTable.get(instruction).getFormat();
    }

    private static void fillOpcodeTable() {
        // TODO: to be reviewed

        //FORMAT 1
        opCodeTable.put("FIX", new InstProp(196, Format.FORMAT1, NONE, NONE));
        opCodeTable.put("FLOAT", new InstProp(192, Format.FORMAT1, NONE, NONE));
        opCodeTable.put("HIO", new InstProp(244, Format.FORMAT1, NONE, NONE));
        opCodeTable.put("NORM", new InstProp(200, Format.FORMAT1, NONE, NONE));
        opCodeTable.put("SIO", new InstProp(240, Format.FORMAT1, NONE, NONE));
        opCodeTable.put("TIO", new InstProp(248, Format.FORMAT1, NONE, NONE));


        //FORMAT 2
        opCodeTable.put("CLEAR", new InstProp(180, Format.FORMAT2, REGISTER, NONE));
        opCodeTable.put("TIXR", new InstProp(184, Format.FORMAT2, REGISTER, NONE));

        opCodeTable.put("SVC", new InstProp(176, Format.FORMAT2, VALUE, NONE));

        opCodeTable.put("SHIFTL", new InstProp(164, Format.FORMAT2, REGISTER, VALUE));
        opCodeTable.put("SHIFTR", new InstProp(168, Format.FORMAT2, REGISTER, VALUE));

        opCodeTable.put("ADDR", new InstProp(144, Format.FORMAT2, REGISTER, REGISTER));
        opCodeTable.put("COMPR", new InstProp(160, Format.FORMAT2, REGISTER, REGISTER));
        opCodeTable.put("DIVR", new InstProp(156, Format.FORMAT2, REGISTER, REGISTER));
        opCodeTable.put("MULR", new InstProp(152, Format.FORMAT2, REGISTER, REGISTER));
        opCodeTable.put("SUBR", new InstProp(148, Format.FORMAT2, REGISTER, REGISTER));
        opCodeTable.put("RMO", new InstProp(172, Format.FORMAT2, REGISTER, REGISTER));


        // FORMAT 3 & 4
        opCodeTable.put("ADD", new InstProp(24, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("ADDF", new InstProp(88, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("AND", new InstProp(64, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("COMP", new InstProp(40, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("COMPF", new InstProp(136, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("DIV", new InstProp(36, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("DIVF", new InstProp(100, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("J", new InstProp(60, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("JEQ", new InstProp(48, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("JGT", new InstProp(52, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("JLT", new InstProp(56, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("JSUB", new InstProp(72, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDA", new InstProp(0, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDB", new InstProp(104, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDCH", new InstProp(80, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDF", new InstProp(112, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDL", new InstProp(8, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDS", new InstProp(108, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDT", new InstProp(116, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LDX", new InstProp(4, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("LPS", new InstProp(208, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("MUL", new InstProp(32, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("MULF", new InstProp(96, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("OR", new InstProp(68, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("RD", new InstProp(216, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("RSUB", new InstProp(76, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("TD", new InstProp(224, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("TIX", new InstProp(44, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("WD", new InstProp(220, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("SSK", new InstProp(236, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STA", new InstProp(12, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STB", new InstProp(120, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STCH", new InstProp(84, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STF", new InstProp(128, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STI", new InstProp(212, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STL", new InstProp(20, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STS", new InstProp(124, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STSW", new InstProp(232, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STT", new InstProp(132, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("STX", new InstProp(16, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("SUB", new InstProp(28, Format.FORMAT3, VALUE, NONE));
        opCodeTable.put("SUBF", new InstProp(92, Format.FORMAT3, VALUE, NONE));
    }

    private static void fillAssemblerDirectives() {
        assemblerDirectives.add("BYTE");
        assemblerDirectives.add("RESB");
        assemblerDirectives.add("WORD");
        assemblerDirectives.add("RESW");
        assemblerDirectives.add("START");
        assemblerDirectives.add("END");
        assemblerDirectives.add("ORG");
        assemblerDirectives.add("LTORG");
        // TODO : Add them all
    }

    public static String getProgramName() {
        return programName;
    }

    public static void setProgramName(String programName) {
        OpcodeTable.programName = programName;
    }

    public static int getStartAddress() {
        return startAddress;
    }

    public static void setStartAddress(int startAddress) {
        OpcodeTable.startAddress = startAddress;
    }

    public static int getProgramLength() {
        return programLength;
    }

    public static void setProgramLength(int programLength) {
        OpcodeTable.programLength = programLength;
    }
}
