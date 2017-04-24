package src.assembler.utils;

import org.junit.Before;
import org.junit.Test;
import src.assembler.datastructures.OpcodeTable;
import src.assembler.datastructures.RegisterTable;

import static org.junit.Assert.*;

/**
 * Created by abdelrahman on 4/24/17.
 */
public class Format_2Test {
    private ObjectBuilder format2Builder;
    
    @Before
    public void setUp() throws Exception {
        format2Builder = new Format_2();
    }

    @Test
    public void checkFieldsResetToDefault() {
        int anyValidOp = 164;
        format2Builder.setOpCode(anyValidOp).setOperand(5).setSecondOperand(6);
        // this should reset fields
        format2Builder.toString();

        String obj = format2Builder.toString();
        assertEquals("Fields do not reset to default after call to toString()", "0000", obj);
    }

    @Test
    public void TestRMO() throws Exception {
        int op = 172;       // op code for RMO
        int r1 = 0, r2 = 1; // register numbers for A and X

        String obj = format2Builder.setOpCode(op).setOperand(r1).setSecondOperand(r2).toString();

        assertEquals("Wrong object code", "AC01", obj);
    }

    @Test
    public void TestSHIFTL() {
        int op = 164, r1 = 0, shamt = 10;
        String obj = format2Builder.setOpCode(op).setOperand(r1).setSecondOperand(shamt).toString();
        assertEquals("Wrong object code", "A40A", obj);
    }

    @Test
    public void TestSHIFTLMaxValue() throws Exception {
        // Test: SHIFTL A,15
        int op = 164, r1 = 0, shamt = 15;
        String obj = format2Builder.setOpCode(op).setOperand(r1).setSecondOperand(shamt).toString();
        assertEquals("Wrong object code", "A40F", obj);
    }

// Exception Tests
    
    @Test(expected = UnsupportedOperationException.class)
    public void setIndirect() throws Exception {
        format2Builder.setIndirect(true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setImmediate() throws Exception {
        format2Builder.setImmediate(true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setIndexed() throws Exception {
        format2Builder.setIndexed(true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setBaseRelative() throws Exception {
        format2Builder.setBaseRelative(true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setPCRelative() throws Exception {
        format2Builder.setPCRelative(true);
    }
    
}