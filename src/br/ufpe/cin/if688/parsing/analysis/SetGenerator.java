package br.ufpe.cin.if688.parsing.analysis;

import java.util.*;

import br.ufpe.cin.if688.parsing.grammar.*;


public final class SetGenerator {
    
    public static Map<Nonterminal, Set<GeneralSymbol>> getFirst(Grammar g) {
        
    	if (g == null) throw new NullPointerException("g nao pode ser nula.");
        
    	Map<Nonterminal, Set<GeneralSymbol>> first = initializeNonterminalMapping(g);
    	
    	/*
    	 * Implemente aqui o método para retornar o conjunto first
    	 */

    	Map<Nonterminal, List<Production>> prods =
    			new HashMap<Nonterminal, List<Production>>();

    	for (Nonterminal nt: g.getNonterminals()) {
    		prods.put(nt, new ArrayList<Production>());
    	}
    	
    	for (Production p: g.getProductions()) {
    		prods.get(p.getNonterminal()).add(p);
    	}
    	
    	for (Nonterminal nt: g.getNonterminals()) {
    		first.get(nt).addAll(getFirstFromNt(nt, prods));
    	}
    	
        return first;
    	
    }

    
    public static Map<Nonterminal, Set<GeneralSymbol>> getFollow(Grammar g, Map<Nonterminal, Set<GeneralSymbol>> first) {
        
        if (g == null || first == null)
            throw new NullPointerException();
                
        Map<Nonterminal, Set<GeneralSymbol>> follow = initializeNonterminalMapping(g);
        
        /*
         * implemente aqui o método para retornar o conjunto follow
         */
        
        return follow;
    }
    
    //método para inicializar mapeamento nãoterminais -> conjunto de símbolos
    private static Map<Nonterminal, Set<GeneralSymbol>>
    initializeNonterminalMapping(Grammar g) {
    	Map<Nonterminal, Set<GeneralSymbol>> result = 
    			new HashMap<Nonterminal, Set<GeneralSymbol>>();

    	for (Nonterminal nt: g.getNonterminals())
    		result.put(nt, new HashSet<GeneralSymbol>());

    	return result;
    }
    
    private static Set<GeneralSymbol> getFirstFromNt(GeneralSymbol sym, Map<Nonterminal, List<Production>> prods) {
    	Set<GeneralSymbol> myFirst = new HashSet<GeneralSymbol>();
    
    	if ((sym instanceof Terminal) || (sym.equals(SpecialSymbol.EPSILON))) {
    		myFirst.add(sym);
    	} else {
    		for (Production p: prods.get((Nonterminal) sym)) {
	    		int n = 0;
	    		
	    		List<GeneralSymbol> sequence = p.getProduction();
	    		
	    		GeneralSymbol symbol;
	    		Set<GeneralSymbol> subset;
	    		
	    		do {
	    			symbol = sequence.get(n++);
	    			subset = getFirstFromNt(symbol, prods);
	    			myFirst.addAll(subset);
	        	} while ((symbol instanceof Nonterminal) && subset.contains(SpecialSymbol.EPSILON));	
    		}
    	}
    	
    	return myFirst;
    }
} 
