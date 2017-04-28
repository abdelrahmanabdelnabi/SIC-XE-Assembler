package src.assembler.datastructures;

/**
 * Created by ahmed on 4/21/17.
 */
public enum OperandType {
    LABEL, REGISTER, NONE,

    // Data for  X'F1'  && C'EOF'
    DATA,
    // Number for @1234 && 0x123
    NUM
}
