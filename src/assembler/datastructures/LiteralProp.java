package src.assembler.datastructures;

import static src.assembler.Common.parseDataOperand;

/**
 * Created by ahmed on 4/26/17.
 */
public class LiteralProp {
    private int literalNumber;
    private String name;
    private int value;
    private int address;
    private boolean isBuilt = false;
    private String objectCode = "";

    public LiteralProp(String name, int literalNumber) {
        this.name = name;
        this.literalNumber = literalNumber;
        value = parseDataOperand(name.substring(1));
        buildObjectCode();
    }

    public static int calcLiteralValue(String literal) {
        return parseDataOperand(literal.substring(1));
    }


    public void buildLiteral(int address) {
        isBuilt = true;
        this.address = address;
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

    private void buildObjectCode() {
        if (name.startsWith("=C")) {
            for (int i = 3; i < name.length() - 1; i++)
                objectCode += Integer.toHexString(name.charAt(i));
        }
        if (name.startsWith("=X")) {
            objectCode += name.substring(3, name.lastIndexOf('\''));
        }
        objectCode = (objectCode + "000000").substring(0, 6).toUpperCase();
    }

    public int getLiteralNumber() {
        return literalNumber;
    }

    public void setLiteralNumber(int literalNumber) {
        this.literalNumber = literalNumber;
    }
}

