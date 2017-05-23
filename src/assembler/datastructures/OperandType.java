package src.assembler.datastructures;

/**
 * Created by ahmed on 4/21/17.
 */
public enum OperandType {
    REGISTER, NONE, VALUE, INVALID;

    public enum VALUE {
        LABEL, DATA, NUM, EXPRESSION, LOCCTR
    }
}

