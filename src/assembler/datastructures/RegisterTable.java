package src.assembler.datastructures;

/**
 * Created by abdelrahman on 4/21/17.
 */
public class RegisterTable {
    public static int getRegisterNumber(String registerName) {
        if(registerName.equals("A"))
            return 0;
        if(registerName.equals("X"))
            return 1;
        if(registerName.equals("L"))
            return 2;
        if(registerName.equals("B"))
            return 3;
        if(registerName.equals("S"))
            return 4;
        if(registerName.equals("T"))
            return 5;
        if(registerName.equals("F"))
            return 6;
        if(registerName.equals("PC"))
            return 8;
        if(registerName.equals("SW"))
            return 9;

        return -1;
    }
}
