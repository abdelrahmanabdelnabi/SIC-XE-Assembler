package src.assembler.datastructures;

import static src.assembler.Common.parseDataOperand;

/**
 * Created by ahmed on 4/26/17.
 */
public class LiteralProp {
    private String name;
    private String object = "";
    private int value;
    private int length;
    private int address;
    private boolean isBuilt = false;

    public LiteralProp(String name) {
        this.name = name;
        value = parseDataOperand(name.substring(1));
        calcLength();
    }

    public static int calcLiteralValue(String literal) {
        return parseDataOperand(literal.substring(1));
    }


    private void calcLength() {
        switch (name.charAt(0)) {
            case 'X':
                length = (name.length() - 2) / 2;
                break;
            case 'C':
                length = name.length() - 3;
        }
    }

    public void buildLiteral(int address) {
        isBuilt = true;
        this.address = address;
    }

    public int getLength() {
        return length;
    }

    public int getAddress() {
        return address;
    }

    public int getValue() {
        return value;
    }

    public boolean isBuilt() {
        return isBuilt;
    }
}

