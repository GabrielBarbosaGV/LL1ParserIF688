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
    	
    	Stack<Nonterminal> currentNt = new Stack<Nonterminal>();
    	Stack<Set<GeneralSymbol>> currentTs = new Stack<Set<GeneralSymbol>>();
    	Stack<Stack<Nonterminal>> currentNts = new Stack<Stack<Nonterminal>>();
    	
    	Map<Nonterminal, List<List<GeneralSymbol>>> prodMap =
    			new HashMap<Nonterminal, List<List<GeneralSymbol>>>();
    	
    	for (Production p: g.getProductions()) {
    		if (prodMap.get(p.getNonterminal()) == null)
    			prodMap.put(p.getNonterminal(), new ArrayList<List<GeneralSymbol>>());
    		
    		prodMap.get(p.getNonterminal()).add(p.getProduction());
    	}
    	
    	currentNt.push(g.getStartSymbol());
    	currentNts.push(new Stack<Nonterminal>());
		currentTs.push(new HashSet<GeneralSymbol>());
    	
    	while(!currentNt.empty()) {
    		for (List<GeneralSymbol> l: prodMap.get(currentNt.peek())) {
    			GeneralSymbol symbol = l.get(0);
    			
    			if (symbol instanceof Nonterminal) {
    				currentNts.peek().push((Nonterminal) symbol);
    			} else {
    				currentTs.peek().add(symbol);
    			}
    		}
    		
    		if (!currentNts.peek().isEmpty()) {
    			currentNt.push(currentNts.peek().pop());
    			currentNts.push(new Stack<Nonterminal>());
    			currentTs.push(new HashSet<GeneralSymbol>());
    		} else {
    			first.put(currentNt.pop(), currentTs.peek());
    			Set<GeneralSymbol> childTerminals = currentTs.pop();
    			currentTs.peek().addAll(childTerminals);
    			currentNts.pop();
    		}
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

} 
