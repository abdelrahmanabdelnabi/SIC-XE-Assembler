package src.assembler.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by abdelrahman on 4/25/17.
 */
public class Format_3Test {
    private final int LDA = 0;
    private ObjectBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new Format_3();
    }

    @Test
    public void checkFieldsResetToDefaults() {
        // build any instruction: ADD #10
        builder.setOpCode(24).setIndirect(false).setImmediate(true).setOperand(10);

        builder.toString(); // first call to toString() should clear flags
        String obj = builder.toString();

        assertEquals("Fields do not reset to default after call to toString()", "000000" , obj);
    }

    @Test
    public void TestSimpleWithConstant() throws Exception {
        // test inst: LDA 50
        // nixbpe = 110000, disp = 032, expected = 030032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(true).setOperand(50)
                .toString();
        assertEquals("Wrong object code for \"LDA 50\"", "030032", obj);
    }

    @Test
    public void TestSimpleAndPCRelative() {
        // test inst: LDA LABEL (where label - PC = 50)
        // nixpbe = 110010, disp = 032, expected = 032032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(true).setPCRelative
                (true).setOperand(50).toString();
        assertEquals("Wrong object code for \"lDA LABEL\" where LABEL - PC = 50", "032032", obj);
    }

    @Test
    public void TestSimpleAndPCBaseRelative() {
        // test inst: LDA LABEL (where label - BASE = 50)
        // nixbpe = 110100, disp = 032, expected = 034032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(true).setBaseRelative
                (true).setOperand(50).toString();
        assertEquals("Wrong object code for \"lDA LABEL\" where LABEL - BASE = 50", "034032", obj);
    }

    @Test
    public void TestSimpleWithConstantAndIndexed() throws Exception {
        // test inst: LDA 50,X
        // nixbpe = 111000, disp = 32, expected = 038032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(true).setIndexed(true)
                .setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA 50,X\"", "038032", obj);
    }

    @Test
    public void TestSimpleWithPCRelativeAndIndexed() throws Exception {
        // test inst: LDA 50,X
        // nixbpe = 111010, disp = 32, expected = 03A032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(true).setIndexed(true)
                .setPCRelative(true).setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA LABEL,X\" where LABEL - PC = 50", "03A032",
                obj);
    }

    @Test
    public void TestSimpleWithBASERelativeAndIndexed() throws Exception {
        // test inst: LDA 50,X
        // nixbpe = 111100, disp = 32, expected = 03C032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(true).setIndexed(true)
                .setBaseRelative(true).setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA LABEL,X\" where LABEL - BASE = 50", "03C032",
                obj);
    }

    @Test
    public void TestIndirectWithConstant() throws Exception {
        // test inst: LDA @50
        // nixbpe = 100000, disp = 32, expected = 020032
        String obj = builder.setOpCode(LDA).setIndirect(true).setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA @50\"", "020032", obj);
    }

    @Test
    public void TestIndirectWithPCRelative() throws Exception {
        // test inst: LDA @LABEL
        // nixbpe = 100010, disp = 32, expected = 022032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(false).setPCRelative(true)
                .setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA @LABEL\" where LABEL - PC = 50", "022032",
                obj);
    }

    @Test
    public void TestIndirectWithBASERelative() throws Exception {
        // test inst: LDA @LABEL
        // nixbpe = 100100, disp = 32, expected = 024032
        String obj = builder.setOpCode(LDA).setIndirect(true).setImmediate(false).setBaseRelative
                (true).setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA @LABEL\" where LABEL - BASE = 50", "024032",
                obj);
    }

    @Test
    public void TestImmediateWithConstant() throws Exception {
        // test inst: LDA #50
        // nixbpe = 010000, disp = 32, expected = 010032
        String obj = builder.setOpCode(LDA).setImmediate(true).setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA #50\"", "010032", obj);
    }

    @Test
    public void TestImmediateWithPCRelative() throws Exception {
        // test inst: LDA #LABEL
        // nixbpe = 010010, disp = 32, expected = 012032
        String obj = builder.setOpCode(LDA).setImmediate(true).setPCRelative(true).setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA #LABEL\" where LABEL - PC = 50", "012032",
                obj);
    }

    @Test
    public void TestImmediateWithBASERelative() throws Exception {
        // test inst: LDA #LABEL
        // nixbpe = 010100, disp = 32, expected = 014032
        String obj = builder.setOpCode(LDA).setImmediate(true).setBaseRelative(true).setOperand(50).toString();
        assertEquals("Wrong object code for \"LDA #LABEL\" where LABEL - BASE = 50", "014032",
                obj);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setSecondOperand() throws Exception {
        builder.setSecondOperand(0);
    }

}