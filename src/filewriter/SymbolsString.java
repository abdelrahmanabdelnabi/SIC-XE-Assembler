package src.filewriter;

import src.assembler.SymbolProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahmed on 4/19/17.
 */
public class SymbolsString implements StringGenerator {
    private HashMap<String, SymbolProperties> symbolTable;
    private StringBuilder symbols;

    public SymbolsString(HashMap<String, SymbolProperties> symbolTable) {
        this.symbolTable = symbolTable;
        symbols = new StringBuilder();
        formString();
    }

    public String toString() {
        return symbols.toString();
    }

    private void formString() {
        symbols.append("===========================================\n");
        symbols.append("                SYMBOL TABLE               \n");
        symbols.append("===========================================\n");
        symbols.append("SYMBOL                         ADDRESS(HEX)\n");
        symbols.append("===========================================\n");
        for (Map.Entry<String, SymbolProperties> cur : symbolTable.entrySet()) {
            symbols.append(String.format("%-30s %-20s\n", cur.getKey(),
                    Integer.toHexString(cur.getValue().getAddress()).toUpperCase()));
        }
        symbols.append("===========================================\n");
    }

}
