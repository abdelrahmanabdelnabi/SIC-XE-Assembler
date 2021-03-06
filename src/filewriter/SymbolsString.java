package src.filewriter;

import src.assembler.datastructures.LiteralProp;
import src.assembler.datastructures.SymbolProp;

import java.util.Map;

/*
 * Created by ahmed on 4/19/17.
 */
public class SymbolsString implements StringGenerator {
    private final Map<String, SymbolProp> symbolTable;
    private final Map<String, LiteralProp> literalsTable;
    private final StringBuilder builder;

    public SymbolsString(Map<String, SymbolProp> symbolTable, Map<String, LiteralProp> literalsTable) {
        this.symbolTable = symbolTable;
        this.literalsTable = literalsTable;
        builder = new StringBuilder();
    }

    public String toString() {
        formSymbolString();
        formLiteralsString();
        return builder.toString();
    }

    private void formSymbolString() {
        builder.append("================================================================\n");
        builder.append("                         SYMBOL TABLE                           \n");
        builder.append("================================================================\n");
        builder.append("SYMBOL                         ADDRESS(HEX)           TYPE      \n");
        builder.append("================================================================\n");
        for (Map.Entry<String, SymbolProp> cur : symbolTable.entrySet()) {
            builder.append(String.format("%-30s %-20s %-10s\n",
                    cur.getKey(),
                    Integer.toHexString(cur.getValue().getAddress()).toUpperCase(),
                    cur.getValue().getType().toString()));
        }
        builder.append("================================================================\n");
    }

    private void formLiteralsString() {
        builder.append("=====================================================\n");
        builder.append("                     LITERAL TABLE                   \n");
        builder.append("=====================================================\n");
        builder.append("LITERAL                  ADDRESS(HEX)      VALUE(HEX)\n");
        builder.append("=====================================================\n");
        for (Map.Entry<String, LiteralProp> cur : literalsTable.entrySet()) {
            builder.append(String.format("%-25s%-18s%-10s\n", cur.getKey(),
                    Integer.toHexString(cur.getValue().getAddress()).toUpperCase(),
                    cur.getValue().getObjectCode()));
        }
        builder.append("=====================================================\n");
    }

}
