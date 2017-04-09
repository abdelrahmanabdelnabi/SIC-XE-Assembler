package parser;

import assembler.datastructures.OpcodeTable;
import org.junit.Before;

import java.util.Arrays;

import static org.junit.Assert.*;
import static parser.InputReader.*;

/**
 * Created by abdelrahman on 4/9/17.
 */
public class ParserTest {

    private static final String OPCODE               =         "RD"          ;
    private static final String OPCODE_LABEL         = "LOOP    STA"           ;
    private static final String OPCODE_OPERAND       =         "STA     BUFFER";
    private static final String OPCODE_LABEL_OPERAND = "LOOP    STA    BUFFER" ;
    private Parser parser;
    private InputReader reader;

    @Before
    public void setUp() {
        reader = new InputReader(InputType.String, OPCODE);
        parser = new Parser(reader);
        OpcodeTable.getOpcodeTable();
    }

    @org.junit.Test
    public void testOpcodeOnly() throws Exception {
        reader = new InputReader(InputType.String, "");
        parser = new Parser(reader);

        reader.setInputString(OPCODE);
        parser.parse();

        assertEquals(1, parser.getParsedInstuctions().size());
        assertEquals( "RD", parser.getParsedInstuctions().get(0).getMnemonic());
        assertEquals( "", parser.getParsedInstuctions().get(0).getLabel());
        assertEquals( "", parser.getParsedInstuctions().get(0).getOperand());
    }

    @org.junit.Test
    public void testOpcodeAndOperand() throws Exception {
        reader.setInputString(OPCODE_OPERAND);
        parser.parse();

        assertEquals( "STA", parser.getParsedInstuctions().get(0).getMnemonic());
        assertEquals( "", parser.getParsedInstuctions().get(0).getLabel());
        assertEquals( "BUFFER", parser.getParsedInstuctions().get(0).getOperand());

    }

    @org.junit.Test
    public void testOpcodeAndLabel() throws Exception {
        reader.setInputString(OPCODE_LABEL);
        parser.parse();

        assertEquals( "STA", parser.getParsedInstuctions().get(0).getMnemonic());
        assertEquals( "LOOP", parser.getParsedInstuctions().get(0).getLabel());
        assertEquals( "", parser.getParsedInstuctions().get(0).getOperand());
    }

    @org.junit.Test
    public void testOpcodeOperandAndLabel() throws Exception {
        reader.setInputString(OPCODE_LABEL_OPERAND);
        parser.parse();

        assertEquals( "STA", parser.getParsedInstuctions().get(0).getMnemonic());
        assertEquals( "LOOP", parser.getParsedInstuctions().get(0).getLabel());
        assertEquals( "BUFFER", parser.getParsedInstuctions().get(0).getOperand());

    }

    @org.junit.Test
    public void testDirectiveOnly() throws Exception {

    }

    @org.junit.Test
    public void testDirectiveAndLabel() throws Exception {

    }

    @org.junit.Test
    public void testDirectiveAndOperand() throws Exception {

    }

    @org.junit.Test
    public void testDirectiveLabelAndOperand() throws Exception {

    }

    @org.junit.Test
    public void testCommendLine() throws Exception {

    }

}