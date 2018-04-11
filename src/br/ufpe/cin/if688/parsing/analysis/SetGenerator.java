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

        return first;
    	
    }

    
    public static Map<Nonterminal, Set<GeneralSymbol>> getFollow(Grammar g, Map<Nonterminal, Set<GeneralSymbol>> first) {
        
        if (g == null || first == null)
            throw new NullPointerException();
                
        Map<Nonterminal, Set<GeneralSymbol>> follow = initializeNonterminalMapping(g);
        
        /*
         * implemente aqui o método para retornar o conjunto follow
         */
        
        follow.get(g.getStartSymbol()).add(SpecialSymbol.EOF);
        
        Map<Nonterminal, List<Production>> prods =
        		new HashMap<Nonterminal, List<Production>>();
        
        for (Nonterminal nt: g.getNonterminals()) {
        	prods.put(nt, new ArrayList<Production>());
        }
        
        for (Production p: g.getProductions()) {
        	prods.get(p.getNonterminal()).add(p);
        }
        
        Set<Nonterminal> nts = new HashSet<Nonterminal>();
        LinkedList<Nonterminal> toExplore = new LinkedList<Nonterminal>();
        toExplore.add(g.getStartSymbol());
        
    	for (Production p: prods.get(toExplore.pop())) {
    		List<GeneralSymbol> sequence = p.getProduction();
	    	for (int i = 0;i < sequence.size();i++) {
	    		GeneralSymbol current = sequence.get(i);
	    		if (current instanceof Nonterminal) {
	    			if (!nts.contains(current)) {
	    				toExplore.add((Nonterminal) current);
	    				nts.add((Nonterminal) current);
	    			}
	    			
	    			if (i < sequence.size() - 1) {
	    				if (sequence.get(i + 1) instanceof Nonterminal) {
	    					follow.get(current).addAll(first.get(sequence.get(i + 1)));
	    					follow.get(current).remove(SpecialSymbol.EPSILON);
	    				} else {
	    					follow.get(current).add(sequence.get(i + 1));
	    				}
	    			}
	    		}
	    	}
    	}
        
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

} 
