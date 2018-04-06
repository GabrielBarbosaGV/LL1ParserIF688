package br.ufpe.cin.if688.table;


import br.ufpe.cin.if688.parsing.analysis.*;
import br.ufpe.cin.if688.parsing.grammar.*;
import java.util.*;


public final class Table {
	private Table() {    }

	public static Map<LL1Key, List<GeneralSymbol>> createTable(Grammar g) throws NotLL1Exception {
        if (g == null) throw new NullPointerException();

        Map<Nonterminal, Set<GeneralSymbol>> first =
            SetGenerator.getFirst(g);
        Map<Nonterminal, Set<GeneralSymbol>> follow =
            SetGenerator.getFollow(g, first);

        Map<LL1Key, List<GeneralSymbol>> parsingTable = 
            new HashMap<LL1Key, List<GeneralSymbol>>();

        /*
         * Implemente aqui o método para retornar a parsing table
         */
        Iterator<Production> productions = g.iterator();
        
        while (productions.hasNext()) {
        	Production next = productions.next();
        	Nonterminal nonTerminal = next.getNonterminal();
        	List<GeneralSymbol> production = next.getProduction();
        	
        	Iterator<GeneralSymbol> symbols =
        			first.get(nonTerminal).iterator();
        	
        	boolean producesEpsilon = false;
        	
        	while (symbols.hasNext()) {
        		GeneralSymbol symbol = symbols.next();
        		
        		if (symbol.equals(SpecialSymbol.EPSILON))
        			producesEpsilon = true;
        		
        		parsingTable.put(new LL1Key(nonTerminal, symbol), production);
        	}
        	
        	if (producesEpsilon) {
        		symbols = follow.get(nonTerminal).iterator();
        		
        		while (symbols.hasNext()) {
        			GeneralSymbol symbol = symbols.next();
        			
        			parsingTable.put(new LL1Key(nonTerminal, symbol),
        					production);
        		}
        	}
        }
        
        return parsingTable;
    }
}
