package src.assembler.core;

import org.junit.Assert;
import org.junit.Test;
import src.assembler.Instruction;
import src.assembler.datastructures.SymbolProp;
import src.assembler.datastructures.LiteralProp;
import src.parser.InputReader;
import src.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by abdelrahman on 4/20/17.
 */
public class PassOneTest {

    @Test
    public void simpleTest() throws Exception {

        List<Instruction> tests = new ArrayList<>();

        tests.add(new Instruction("COPY", "START", "0", 1));
        tests.add(new Instruction("FIRST", "STL", "RETADR", 2));
        tests.add(new Instruction("CLOOP", "+JSUB", "RDREC", 3));
        tests.add(new Instruction("", "COMP", "#0", 4));
        tests.add(new Instruction("RETADR", "RESW", "1", 5));
        tests.add(new Instruction("", "END", "", 6));


        Assembler assembler = new Assembler(tests);
        PassOne passOne = new PassOne(tests);
        passOne.execute();

        List<Instruction> actualOutput = passOne.getInstructions();

        List<Instruction> correctOutput = new ArrayList<>(tests);
        correctOutput = correctOutput.subList(1, correctOutput.size());

        correctOutput.get(0).setAddress(0);
        correctOutput.get(1).setAddress(3);
        correctOutput.get(2).setAddress(7);
        correctOutput.get(3).setAddress(10);
        correctOutput.get(4).setAddress(10);

        assertEquals("Incorrect number of output instructions", correctOutput.size(),
                actualOutput.size());

        for (int i = 0; i < correctOutput.size(); i++) {
            assertEquals(correctOutput.get(i).getAddress(), actualOutput.get(i).getAddress());
        }

        for (String s : passOne.getSymbolTable().keySet())
            System.out.println(s);

        assertEquals("Symbol Table doesn't contain the correct number of symbols", 4, passOne
                .getSymbolTable().size());

        Assert.assertEquals(passOne.getSymbolTable().get("COPY").getAddress(), new SymbolProp
                (0).getAddress());
        assertEquals(passOne.getSymbolTable().get("FIRST").getAddress(), new SymbolProp
                (0).getAddress());
        assertEquals(passOne.getSymbolTable().get("CLOOP").getAddress(), new SymbolProp
                (3).getAddress());
        assertEquals(passOne.getSymbolTable().get("RETADR").getAddress(), new SymbolProp
                (10).getAddress());

    }

    @Test
    public void testLiterals() {
        String literalsFile = System.getProperty("user.dir") + "/src/tests/literals/literals.asm";

        InputReader inputReader = new InputReader(InputReader.InputType.File, literalsFile);

        Parser parser = new Parser(inputReader);
        parser.parse();

        Assembler assembler = new Assembler(parser.getParsedInstuctions());
        assembler.executePassOne();

        HashMap<String, LiteralProp> literalTable = assembler.getLiteralsTable();
        for (Map.Entry<String, LiteralProp> literal : literalTable.entrySet()) {
            System.out.println(String.format("%-15s %-5s", literal.getKey(), literal.getValue().getAddress()));
        }
    }

}