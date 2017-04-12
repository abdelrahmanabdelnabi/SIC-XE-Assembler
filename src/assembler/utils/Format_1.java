package src.assembler.utils;

import src.assembler.Instruction;

import static src.assembler.datastructures.OpcodeTable.getHexOpcode;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_1 implements ObjectBuilder {
    @Override
    public String getObjectCode(Instruction inst) {
        return getHexOpcode(inst.getMnemonic());
    }
}
