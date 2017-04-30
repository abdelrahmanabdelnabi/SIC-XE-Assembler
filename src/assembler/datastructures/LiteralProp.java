package src.assembler.datastructures;

import static src.assembler.Common.parseDataOperand;

/**
 * Created by ahmed on 4/26/17.
 */
public class LiteralProp {
    private String name;
    private int value;
    private int length;
    private int address;
    private boolean isBuilt = false;
    private String objectCode;

    public LiteralProp(String name) {
        this.name = name;
        value = parseDataOperand(name.substring(1));
        calcLength();
    }

    public static int calcLiteralValue(String literal) {
        return parseDataOperand(literal.substring(1));
    }


    private void calcLength() {
        switch (name.substring(1).charAt(0)) {
            case 'X':
                length = (name.length() - 3) / 2;
                break;
            case 'C':
                length = name.length() - 4;
        }
    }

    public void buildLiteral(int address) {
        isBuilt = true;
        this.address = address;
        objectCode = Integer.toHexString(value).toUpperCase();
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

    public String getName() {
        return this.name;
    }

    public String getObjectCode() {
        return objectCode;
    }
}

