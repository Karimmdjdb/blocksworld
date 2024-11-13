package blocksworld.cp.heuristics;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import blocksworld.modelling.variables.Variable;

// Heuristique sur la taille des domaines des variables.
public class DomainSizeVariableHeuristic implements VariableHeuristic {
    private boolean graterDomain;

    public DomainSizeVariableHeuristic(boolean graterDomain) {
        this.graterDomain = graterDomain;
    }

    // Retourne la variable avec le plus grand domaine (si graterDomain = true),
    // ou la variable avec le plus petit demaine (si graterDomain = false).
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable best = null;
        int min = Integer.MAX_VALUE, max = 0;
        Map<Variable, Integer> domainSizes = new HashMap<>();
        for(Variable var : variables) {
            int size = domains.get(var).size();
            domainSizes.put(var, size);
            if(graterDomain) {
                if(size > max) {
                    max = size;
                    best = var;
                }
            }
            else {
                if(size < min) {
                    min = size;
                    best = var;
                }
            }
        }
        return best;
    }
}
