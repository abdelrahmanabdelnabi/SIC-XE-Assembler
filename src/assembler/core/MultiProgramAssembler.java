package src.assembler.core;

import src.assembler.datastructures.Instruction;
import src.assembler.datastructures.LiteralProp;
import src.assembler.datastructures.SymbolProp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abdelrahman on 5/24/17.
 *
 * An assembler for multiple control sections
 *
 */
public class MultiProgramAssembler {
    private List<Assembler> singleProgramAssemblers;

    public MultiProgramAssembler(List<Instruction> code) {
        // split the code into separate programs at each CSECT directive
        singleProgramAssemblers = new ArrayList<>();

        int programStartIndex = 0;

        for(int i = 0; i < code.size(); i++) {
            if(code.get(i).getMnemonic().equals("CSECT")) {
                singleProgramAssemblers.add(new Assembler(code.subList(programStartIndex, i)));
                programStartIndex = i;
            } else if(code.get(i).getMnemonic().equals("END")) {
                singleProgramAssemblers.add(new Assembler(code.subList(programStartIndex, i + 1)));
            }
        }
    }

    public void executePassOne() {
        for(Assembler asm : singleProgramAssemblers) {
            asm.executePassOne();
        }
    }

    public void executePassTwo() {
        for(Assembler asm : singleProgramAssemblers)
            asm.executePassTwo();
    }

    public String[] getObjectCode() {
        String[] programsCode = new String[singleProgramAssemblers.size()];

        for(int i = 0; i < programsCode.length; i++)
            programsCode[i] = singleProgramAssemblers.get(i).getObjectCode2();

        return programsCode;
    }

    public List<Map<String, SymbolProp>> getSymbolTables() {
        int size = singleProgramAssemblers.size();

        List<Map<String, SymbolProp>> tables = new ArrayList<>(size);

        for(Assembler asm : singleProgramAssemblers)
            tables.add(asm.getSymbolTable());

        return tables;
    }

    public List<Map<String, LiteralProp>> getLiteralsTables() {
        int size = singleProgramAssemblers.size();

        List<Map<String, LiteralProp>> tables = new ArrayList<>(size);

        for(Assembler asm : singleProgramAssemblers)
            tables.add(asm.getLiteralsTable());

        return tables;
    }
}
