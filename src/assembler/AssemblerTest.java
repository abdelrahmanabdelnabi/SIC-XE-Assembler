package src.assembler;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by abdelrahman on 4/20/17.
 */
public class AssemblerTest {

    @Test
    public void executePassOne() throws Exception {

        List<Instruction> tests = new ArrayList<>();

        tests.add(new Instruction("COPY", "START", "0", 1));
        tests.add(new Instruction("FIRST", "STL", "RETADR", 2));
        tests.add(new Instruction("CLOOP", "+JSUB", "RDREC", 3));
        tests.add(new Instruction("", "COMP", "#0", 4));
        tests.add(new Instruction("RETADR", "RESW", "1", 5));
        tests.add(new Instruction("", "END", "", 6));


        Assembler assembler = new Assembler(tests);

        assembler.executePassOne();

        List<Instruction> actualOutput = assembler.getInstructions();

        List<Instruction> correctOutput = new ArrayList<>(tests);
        correctOutput = correctOutput.subList(1, correctOutput.size());

        correctOutput.get(0).setAddress(0);
        correctOutput.get(1).setAddress(3);
        correctOutput.get(2).setAddress(7);
        correctOutput.get(3).setAddress(10);
        correctOutput.get(4).setAddress(10);

        assertEquals("Incorrect number of output instructions", correctOutput.size(),
                actualOutput.size
                ());

        for(int i = 0; i < correctOutput.size(); i++) {
            assertEquals(correctOutput.get(i).getAddress(), actualOutput.get(i).getAddress());
        }

        for (String s : assembler.getSymbolTable().keySet())
                System.out.println(s);

        assertEquals("Symbol Table doesn't contain the correct number of symbols", 4, assembler
                .getSymbolTable().size());

        assertEquals( assembler.getSymbolTable().get("COPY").getAddress(), new SymbolProperties
                (0).getAddress());
        assertEquals(assembler.getSymbolTable().get("FIRST").getAddress(), new SymbolProperties
                (0).getAddress());
        assertEquals( assembler.getSymbolTable().get("CLOOP").getAddress(), new SymbolProperties
                (3).getAddress());
        assertEquals( assembler.getSymbolTable().get("RETADR").getAddress(), new SymbolProperties
                (10).getAddress());

    }


}