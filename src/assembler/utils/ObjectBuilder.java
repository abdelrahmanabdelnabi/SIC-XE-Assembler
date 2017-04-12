package src.assembler.utils;

import src.assembler.Instruction;

/**
 * Created by ahmed on 4/12/17.
 */
public interface ObjectBuilder {
    public String getObjectCode(Instruction inst);
}
