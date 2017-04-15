package src.assembler.utils;

import src.assembler.Logger;
import src.assembler.datastructures.Format;
import src.assembler.datastructures.OpcodeTable;

import java.util.HashMap;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_2 extends ObjectBuilder {
    private HashMap<String, String> Registers;

    public Format_2() {
        Registers = new HashMap<>();
        buildRegisters();
    }

    @Override
    public String toString() {
        return OpcodeTable.getHexOpcode(inst.getMnemonic()) + parseOperand(inst.getOperand());
    }

    @Override
    void setIndirect(boolean isIndirect) {

    }

    @Override
    void setImmediate(boolean isImmediate) {

    }

    @Override
    void setIndexed(boolean isIndirect) {

    }

    @Override
    void setBaseRelative(boolean isBaseRelative) {

    }

    @Override
    void setPCRelative(boolean isPCRelative) {

    }

    @Override
    void setOperand(int operand) {

    }

    @Override
    Format getFormat() {
        return null;
    }

    private String parseOperand(String operand) {
        String[] registers = operand.split(",");
        if (registers.length != 2 || Registers.get(registers[0]) == null || Registers.get(registers[1]) == null) {
            // TODO Throw Exception
            Logger.Log("Error, Can't detect registers ");
        }
        return Registers.get(registers[0]) + Registers.get(registers[1]);
    }

    private void buildRegisters() {
        Registers.put("A", "0");
        Registers.put("X", "1");
        Registers.put("L", "2");
        Registers.put("B", "3");
        Registers.put("S", "4");
        Registers.put("T", "5");
        Registers.put("F", "6");
        Registers.put("PC", "8");
        Registers.put("SW", "9");
    }
}
